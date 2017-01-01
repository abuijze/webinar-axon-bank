package org.axonframework.sample.axonbank.transfer;

import org.axonframework.sample.axonbank.coreapi.*;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

public class MoneyTransferSagaTest {
    private SagaTestFixture fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new SagaTestFixture<>(MoneyTransferSaga.class);
    }

    @Test
    public void testMoneyTransferRequest() throws Exception {
        fixture.givenNoPriorActivity()
                .whenPublishingA(new MoneyTransferRequestedEvent("tf1", "acct1", "acct2", 100))
                .expectActiveSagas(1)
                .expectDispatchedCommands(new WithdrawMoneyCommand("acct1", "tf1", 100));
    }

    @Test
    public void testDepositMoneyAfterWithdrawal() throws Exception {
        fixture.givenAPublished(new MoneyTransferRequestedEvent("tf1", "acct1", "acct2", 100))
                .whenPublishingA(new MoneyWithdrawnEvent("acct1", "tf1", 100, 500))
                .expectDispatchedCommands(new DepositMoneyCommand("acct2", "tf1", 100));

    }

    @Test
    public void testTransferCompletedAfterDeposit() throws Exception {
        fixture.givenAPublished(new MoneyTransferRequestedEvent("tf1", "acct1", "acct2", 100))
                .andThenAPublished(new MoneyWithdrawnEvent("acct1", "tf1", 100, 500))
                .whenPublishingA(new MoneyDepositedEvent("acct2", "tf1", 100, 400))
                .expectDispatchedCommands(new CompleteMoneyTransferCommand("tf1"));

    }

    @Test
    public void testSagaEndsAfterTransactionCompleted() throws Exception {
        fixture.givenAPublished(new MoneyTransferRequestedEvent("tf1", "acct1", "acct2", 100))
                .andThenAPublished(new MoneyWithdrawnEvent("acct1", "tf1", 100, 500))
                .andThenAPublished(new MoneyDepositedEvent("acct2", "tf1", 100, 400))
                .whenPublishingA(new MoneyTransferCompletedEvent("tf1"))
                .expectActiveSagas(0)
                .expectNoDispatchedCommands();

    }
}
