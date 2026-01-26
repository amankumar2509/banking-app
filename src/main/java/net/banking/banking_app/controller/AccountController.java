package net.banking.banking_app.controller;

import net.banking.banking_app.dto.AccountDto;
import net.banking.banking_app.dto.TransferFundDto;
import net.banking.banking_app.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //add account rest api
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get account Rest API
    @GetMapping("/{id}")
    public  ResponseEntity<AccountDto> getAccountByID(@PathVariable long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }
    //Deposit REST API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id ,@RequestBody Map<String, Double> request){
        double amount =request.get("amount");
        AccountDto accountDto = accountService.deposit(id,amount);
        return ResponseEntity.ok(accountDto);

    }
    //Withdraw REST API
    @PutMapping("/{id}/withdraw")
    public  ResponseEntity<AccountDto>withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request){
        double amount=request.get("amount");
         AccountDto accountDto= accountService.withdraw(id,amount);
         return ResponseEntity.ok(accountDto);
    }
    //Get All Accounts REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
       List<AccountDto> accounts= accountService.getAllAccounts();
       return  ResponseEntity.ok(accounts);
    }

    //Delete Account Rest API

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted successfully!");
    }

    //Transfer API
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFund( @RequestBody TransferFundDto transferFundDto){
        accountService.transferFunds(transferFundDto);
        return ResponseEntity.ok("Transfer Successful");

    }
}
