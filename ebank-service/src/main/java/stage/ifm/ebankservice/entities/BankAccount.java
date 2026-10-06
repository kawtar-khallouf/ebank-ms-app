package stage.ifm.ebankservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import stage.ifm.ebankservice.model.Customer;

import java.util.Date;
@Entity @Data @NoArgsConstructor
@AllArgsConstructor @Builder
public class BankAccount {
    @Id
    private String id;
    private Date createAt;
    private double balance;
    private String type;
    private Long customerId;
    @Transient
    private Customer customer;
}
