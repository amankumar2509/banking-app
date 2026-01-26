package net.banking.banking_app.service.impl;

import jakarta.transaction.Transactional;
import net.banking.banking_app.dto.AccountDto;
import net.banking.banking_app.dto.TransferFundDto;
import net.banking.banking_app.entity.Account;
import net.banking.banking_app.exception.AccountException;
import net.banking.banking_app.mapper.AccountMapper;
import net.banking.banking_app.repository.AccountRepository;
import net.banking.banking_app.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {

        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount= accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new AccountException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new AccountException("Account does not exist"));

       double total = account.getBalance() + amount;
       account.setBalance(total);
       Account savedAccount = accountRepository.save(account);
       return AccountMapper.mapToAccountDto(savedAccount);
    }

    public  AccountDto withdraw(Long id, double amount){
        Account account= accountRepository.findById(id).orElseThrow(()-> new AccountException("Account does not exist"));
        if(account.getBalance()<amount){
            throw  new RuntimeException("Insufficient balance");
        }
        double total= account.getBalance()-amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts=accountRepository.findAll();
        return accounts.stream().map((account)->AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id).orElseThrow(()-> new AccountException("Account does not exist"));
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void transferFunds(TransferFundDto transferFundDto){
        if (transferFundDto.amount() <= 0) {
            throw new AccountException("Transfer amount must be greater than zero");
        }

        if (transferFundDto.fromAccountId()
                .equals(transferFundDto.toAccountId())) {
            throw new AccountException("Cannot transfer to same account");
        }


        //retrive the account from which we need to send amount

       Account fromAccount= accountRepository.findById(transferFundDto.fromAccountId()).orElseThrow(()->new AccountException(" Sender account not found"));
       Account toAccount= accountRepository.findById(transferFundDto.toAccountId()).orElseThrow(()->new AccountException("Receiver account  not found"));

        if (fromAccount.getBalance() < transferFundDto.amount()) {
            throw new AccountException("Insufficient balance");
        }

        //debit the amount from fromAccount object
        fromAccount.setBalance(fromAccount.getBalance()- transferFundDto.amount());

        //credit the amount to toAccount
        toAccount.setBalance(toAccount.getBalance()+ transferFundDto.amount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

    }


}
