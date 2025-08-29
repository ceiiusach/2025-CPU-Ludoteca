package seminfcpu.ludoteca.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.dto.LoanDto;
import seminfcpu.ludoteca.entity.Item;
import seminfcpu.ludoteca.entity.Loan;
import seminfcpu.ludoteca.exception.ItemNotAvailableException;
import seminfcpu.ludoteca.persistence.LoanRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public final class LoanService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<UUID, ScheduledFuture<?>> runningLoans = new LinkedHashMap<>();
    private final UserService userService;
    private final ItemService itemService;
    private final EmailService emailService;
    private final LoanRepository repository;

    public LoanService(@NotNull UserService userService, @NotNull ItemService itemService, @NotNull EmailService emailService, @NotNull LoanRepository repository) {
        this.userService = userService;
        this.itemService = itemService;
        this.emailService = emailService;
        this.repository = repository;
    }

    @NotNull
    public Loan create(@NotNull LoanDto loanDto) throws ItemNotAvailableException {
        Item item = itemService.getById(loanDto.getItemId()).orElseThrow();
        if (!item.isAvailable()) throw new ItemNotAvailableException();

        Loan loan = new Loan();
        loan.setUser(userService.getById(loanDto.getUserId()).orElseThrow());
        loan.setItem(item);
        loan.setEstimatedMinutes(loanDto.getEstimatedMinutes());
        loan.setDate(LocalDateTime.now());
        loan.setPending(true);
        loan = repository.save(loan);

        item.decreaseStock();
        itemService.update(item);

        return loan;
    }

    @NotNull
    public List<Loan> getAll() {
        return repository.findAll();
    }

    @NotNull
    public List<Loan> getByUserId(@NotNull UUID id) {
        return repository.findByUserId(id);
    }

    @NotNull
    public Optional<Loan> getById(@NotNull UUID id) {
        return repository.findById(id);
    }

    @NotNull
    public List<Loan> getAllPending() {
        return repository.findByPending(true);
    }

    @NotNull
    public Loan update(@NotNull Loan loan) {
        Loan oldLoan = getById(loan.getId()).orElseThrow();
        if (oldLoan.isPending() && !loan.isPending()) {
            oldLoan.setPending(false);
            oldLoan.setDate(LocalDateTime.now());
            Loan newLoan = repository.save(oldLoan);
            runningLoans.put(newLoan.getId(), scheduler.schedule(() -> {
                if (getById(newLoan.getId()).isPresent()) emailService.sendDelayNotice(newLoan);
                runningLoans.remove(newLoan.getId());
            }, newLoan.getEstimatedMinutes(), TimeUnit.MINUTES));
            return newLoan;
        }
        oldLoan.setPending(loan.isPending());
        return repository.save(loan);
    }

    public void delete(@NotNull UUID id) {
        repository.deleteById(id);
        if (runningLoans.containsKey(id)) {
            runningLoans.get(id).cancel(true);
            runningLoans.remove(id);
        }
    }
}
