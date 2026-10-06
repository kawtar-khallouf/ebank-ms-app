package stage.ifm.ebankservice.services;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;
import stage.ifm.ebankservice.entities.BankAccount;
import stage.ifm.ebankservice.feign.CustomerRestClient;
import stage.ifm.ebankservice.model.Customer;
import stage.ifm.ebankservice.repository.BankAccountRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRestClient customerRestClient;

    public EbankService(BankAccountRepository bankAccountRepository, CustomerRestClient customerRestClient) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRestClient = customerRestClient;
    }

    @McpTool(description = "Get all bank accounts")
    public List<BankAccount> getAllBankAccount() {
        return bankAccountRepository.findAll();
    }
    @McpTool(description = "Get a bank account by id")
    public BankAccount getBankAccountById( @McpToolParam(description = "The bank account id") String id) {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(()-> new RuntimeException("Bank Account Not Found"));
        bankAccount.setCustomer(customerRestClient.getCustomerById(bankAccount.getCustomerId()));
        return bankAccount;
    }

    @McpTool(description = "Save a new bank Account")
    public BankAccount save(@McpToolParam(description = "The bank account to save(balance,type,customerId)") BankAccount bankAccount) {
        try {
            Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreateAt(new Date());
            return bankAccountRepository.save(bankAccount);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
