package org.axonframework.sample.axonbank.query;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.sample.axonbank.coreapi.BalanceUpdatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountBalanceEventHandler {

    private final AccountBalanceRepository repository;

    public AccountBalanceEventHandler(AccountBalanceRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(BalanceUpdatedEvent event) {
        repository.save(new AccountBalance(event.getAccountId(), event.getBalance()));
    }

    @GetMapping("/balance/{id}")
    public AccountBalance getBalance(@PathVariable String id) {
        return repository.findOne(id);
    }
}
