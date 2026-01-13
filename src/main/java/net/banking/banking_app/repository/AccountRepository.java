package net.banking.banking_app.repository;

import net.banking.banking_app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long > {

}
