package org.axonframework.sample.axonbank;

import org.axonframework.config.EventHandlingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AxonBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AxonBankApplication.class, args);
    }

    @Autowired
    public void configure(EventHandlingConfiguration config) {
        config.registerTrackingProcessor("org.axonframework.sample.axonbank.query");
    }

}
