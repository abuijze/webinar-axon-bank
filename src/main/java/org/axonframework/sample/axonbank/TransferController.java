package org.axonframework.sample.axonbank;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.sample.axonbank.coreapi.CreateAccountCommand;
import org.axonframework.sample.axonbank.coreapi.RequestMoneyTransferCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TransferController {

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping("/")
    @ResponseBody
    public String sendMessage() {

        commandGateway.send(new CreateAccountCommand("1234", 1000), LoggingCallback.INSTANCE);
        commandGateway.send(new CreateAccountCommand("4321", 1000), LoggingCallback.INSTANCE);
        commandGateway.send(new RequestMoneyTransferCommand("tf1", "1234", "4321", 100), LoggingCallback.INSTANCE);

        return "OK";
    }
}
