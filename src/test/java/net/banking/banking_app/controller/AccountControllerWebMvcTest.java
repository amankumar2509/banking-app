package net.banking.banking_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.banking.banking_app.dto.AccountDto;
import net.banking.banking_app.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addAccount_shouldReturn201Created() throws Exception {

        AccountDto requestDto = new AccountDto(null, "abc", 1000);
        AccountDto responseDto = new AccountDto(1L, "abc", 1000);

        when(accountService.createAccount(requestDto))
                .thenReturn(responseDto);

        mockMvc.perform(
                        post("/api/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountHolderName").value("abc"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

}
