package net.banking.banking_app.controller;

import net.banking.banking_app.dto.AccountDto;
import net.banking.banking_app.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    void testAddAccount() {
        // GIVEN (input + expected output)
        AccountDto inputDto = new AccountDto(null, "abc", 1000);
        AccountDto savedDto = new AccountDto(1L, "abc", 1000);

        when(accountService.createAccount(inputDto)).thenReturn(savedDto);

        // WHEN (call controller)
        ResponseEntity<AccountDto> response =
                accountController.addAccount(inputDto);

        // THEN (verify response)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedDto, response.getBody());
    }

    void testGetAccountById(){
        long accountId=1L;
        AccountDto accountDto=new AccountDto(1L,"abc",1000);
        when(accountService.getAccountById(accountId)).thenReturn(accountDto);
        ResponseEntity<AccountDto> response=accountController.getAccountByID(accountId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
        verify(accountService).getAccountById(accountId);

    }
    void testDeposit(){
        Long accountId = 1L;
        double amount= 1000.0;
        Map<String,Double> request= new HashMap<>();
        request.put("amount", amount);
        AccountDto updatedAccount = new AccountDto(1L,"abc",1500);
        when(accountService.deposit(accountId,amount)).thenReturn(updatedAccount);

        ResponseEntity<AccountDto> response= accountController.deposit(accountId,request);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(updatedAccount, response.getBody());

    }
}
