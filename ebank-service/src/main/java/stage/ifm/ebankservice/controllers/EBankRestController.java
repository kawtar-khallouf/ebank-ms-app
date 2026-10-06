package stage.ifm.ebankservice.controllers;

import org.springframework.web.bind.annotation.*;
import stage.ifm.ebankservice.entities.BankAccount;
import stage.ifm.ebankservice.services.EbankService;

import java.util.List;

@RestController
public class EBankRestController {

    private EbankService ebankService;

    public EBankRestController(EbankService ebankService) {
        this.ebankService = ebankService;
    }
    @GetMapping("/accounts")
    public List<BankAccount> getAllBankAccount() {
        return ebankService.getAllBankAccount();
    }
    @GetMapping("/accounts/{id}")
    public BankAccount getBankAccountById(@PathVariable String id) {
        return ebankService.getBankAccountById(id);
    }
    @PostMapping("/accounts")
    public BankAccount save(@RequestBody BankAccount bankAccount) {
        return ebankService.save(bankAccount);
    }
}
