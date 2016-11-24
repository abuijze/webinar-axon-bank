package org.axonframework.sample.axonbank;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.sample.axonbank.coreapi.CreateAccountCommand;
import org.axonframework.sample.axonbank.coreapi.RequestMoneyTransferCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class TransferController {

    @Autowired
    private CommandGateway commandGateway;

    @GetMapping("/init")
    public String sendMessage() {

        commandGateway.send(new CreateAccountCommand("1234", 1000), LoggingCallback.INSTANCE);
        commandGateway.send(new CreateAccountCommand("4321", 1000), LoggingCallback.INSTANCE);
        commandGateway.send(new RequestMoneyTransferCommand("tf1", "1234", "4321", 100), LoggingCallback.INSTANCE);

        return "OK";
    }

    @GetMapping("/transfer/{sourceAccount}/{destinationAccount}/{amount}")
    public CompletableFuture<Object> transfer(@PathVariable String sourceAccount, @PathVariable String destinationAccount, @PathVariable int amount) {
        String transferId = UUID.randomUUID().toString();
        return commandGateway.send(new RequestMoneyTransferCommand(transferId, sourceAccount, destinationAccount, amount))
                .exceptionally(e -> e);
    }

    @GetMapping("/create/{accountId}/{overdraftLimit}")
    public CompletableFuture<Object> createAccount(@PathVariable String accountId, @PathVariable int overdraftLimit) {
        return commandGateway.send(new CreateAccountCommand(accountId, overdraftLimit));
    }

    @GetMapping("/create/{overdraftLimit}")
    public CompletableFuture<Object> createAccount(@PathVariable int overdraftLimit) {
        return createAccount(UUID.randomUUID().toString(), overdraftLimit);
    }
}
