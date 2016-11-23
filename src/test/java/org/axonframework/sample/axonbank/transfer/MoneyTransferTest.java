package org.axonframework.sample.axonbank.transfer;

import org.axonframework.sample.axonbank.coreapi.CompleteMoneyTransferCommand;
import org.axonframework.sample.axonbank.coreapi.MoneyTransferCompletedEvent;
import org.axonframework.sample.axonbank.coreapi.MoneyTransferRequestedEvent;
import org.axonframework.sample.axonbank.coreapi.RequestMoneyTransferCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class MoneyTransferTest {

    private FixtureConfiguration<MoneyTransfer> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(MoneyTransfer.class);
    }

    @Test
    public void createNewTransaction() throws Exception {
        fixture.givenNoPriorActivity()
                .when(new RequestMoneyTransferCommand("abcd", "1234", "2345", 100))
                .expectEvents(new MoneyTransferRequestedEvent("abcd", "1234", "2345", 100));
    }

    @Test
    public void finishTransaction() throws Exception {
        fixture.given(new MoneyTransferRequestedEvent("abcd", "1234", "2345", 100))
                .when(new CompleteMoneyTransferCommand("abcd"))
                .expectEvents(new MoneyTransferCompletedEvent("abcd"));
    }
}
