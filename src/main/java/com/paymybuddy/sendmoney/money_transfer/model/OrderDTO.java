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
    
    @Getter
    @Setter
    private String beneficiary;
    
    @Getter
    @Setter
    private double amount;
    
    @Getter
    @Setter
    private String sender; 
    
}
