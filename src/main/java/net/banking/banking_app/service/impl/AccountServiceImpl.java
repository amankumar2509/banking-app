package net.banking.banking_app.service.impl;

import net.banking.banking_app.dto.AccountDto;
import net.banking.banking_app.entity.Account;
import net.banking.banking_app.mapper.AccountMapper;
import net.banking.banking_app.repository.AccountRepository;
import net.banking.banking_app.service.AccountService;
import org.springframework.stereotype.Service;

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

}
