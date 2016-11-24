package org.axonframework.sample.axonbank.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.sample.axonbank.coreapi.MoneyDepositedEvent;
import org.axonframework.sample.axonbank.coreapi.MoneyWithdrawnEvent;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Example.*;

@RestController
public class TransactionHistoryEventHandler {

    private final TransactionHistoryRepository repository;

    public TransactionHistoryEventHandler(TransactionHistoryRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(MoneyDepositedEvent event) {
        repository.save(new TransactionHistory(event.getAccountId(), event.getTransactionId(), event.getAmount()));
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent event) {
        repository.save(new TransactionHistory(event.getAccountId(), event.getTransactionId(), -event.getAmount()));
    }

    @GetMapping("/history/{accountId}")
    public List<TransactionHistory> findTransactions(@PathVariable String accountId) {
        return repository.findAll(Example.of(new TransactionHistory(accountId)));
    }
}
