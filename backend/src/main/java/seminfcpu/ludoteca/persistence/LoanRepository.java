package seminfcpu.ludoteca.persistence;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seminfcpu.ludoteca.entity.Loan;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {
    @NotNull List<Loan> findByUserId(UUID id);
    @NotNull List<Loan> findByPending(boolean pending);
}
