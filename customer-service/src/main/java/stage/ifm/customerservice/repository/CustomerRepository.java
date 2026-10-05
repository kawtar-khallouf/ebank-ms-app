package stage.ifm.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stage.ifm.customerservice.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
