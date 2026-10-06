package stage.ifm.ebankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stage.ifm.ebankservice.entities.BankAccount;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByCustomerId(Long customerId);
}
