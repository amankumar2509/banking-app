package net.banking.banking_app.repository;

import net.banking.banking_app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction,Long> {
    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);


}
