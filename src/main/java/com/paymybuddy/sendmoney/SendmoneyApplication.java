package com.paymybuddy.sendmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SendmoneyApplication {

    /**
     * Main function, entry point of the application.
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(SendmoneyApplication.class, args);
    }

    /**
     * Private empty constructor.
     */
    protected SendmoneyApplication() {
    }
}
