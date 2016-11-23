package org.axonframework.sample.axonbank.transfer;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.sample.axonbank.coreapi.*;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

@NoArgsConstructor
@Aggregate
public class MoneyTransfer {

    @AggregateIdentifier
    private String transferId;

    @CommandHandler
    public MoneyTransfer(RequestMoneyTransferCommand cmd) {
        apply(new MoneyTransferRequestedEvent(cmd.getTransferId(), cmd.getSourceAccount(), cmd.getTargetAccount(),
                                              cmd.getAmount()));
    }

    @CommandHandler
    public void handle(CompleteMoneyTransferCommand cmd) {
        apply(new MoneyTransferCompletedEvent(transferId));
    }

    @CommandHandler
    public void handle(CancelMoneyTransferCommand cmd) {
        apply(new MoneyTransferCancelledEvent(transferId));
    }

    @EventSourcingHandler
    protected void on(MoneyTransferRequestedEvent event) {
        this.transferId = event.getTransferId();
    }

    @EventSourcingHandler
    protected void on (MoneyTransferCompletedEvent event) {
        markDeleted();
    }
}
