package com.paymybuddy.sendmoney.money_transfer.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The OrderDTO class is the data transfer object used to carry data of a money
 * transfer order.
 *
 * @author Thierry SCHREINER
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    /**
     * The email address of the transfer beneficiary.
     */
    @Getter
    @Setter
    private String beneficiary;

    /**
     * The amount of the money transfer.
     */
    @Getter
    @Setter
    private double amount;

    /**
     * The email address of the transfer sender.
     */
    @Getter
    @Setter
    private String sender;

}
