package seminfcpu.ludoteca.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.dto.LoanDto;
import seminfcpu.ludoteca.entity.Item;
import seminfcpu.ludoteca.entity.Loan;
import seminfcpu.ludoteca.exception.ItemNotAvailableException;
import seminfcpu.ludoteca.persistence.LoanRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public final class LoanService {
    private final UserService userService;
    private final ItemService itemService;
    private final LoanRepository repository;

    public LoanService(UserService userService, ItemService itemService, @NotNull LoanRepository repository) {
        this.userService = userService;
        this.itemService = itemService;
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
        return repository.save(loan);
    }

    public void delete(@NotNull UUID id) {
        repository.deleteById(id);
    }
}
