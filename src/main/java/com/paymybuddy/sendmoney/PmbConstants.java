package com.paymybuddy.sendmoney;

/**
 * @author Thierry SCHREINER
 *
 */
public final class PmbConstants {

    /**
     * This constant defines the value of the fee rate of PMB transfer.
     */
    public static final double FEE_RATE = 0.005; // 0.5%

    /**
     * This constant contains the value of 100, use to round operations.
     */
    public static final double CENT = 100;
    
    /**
     * This constant contains the email of the SendMoney application.
     */
    public static final String SEND_MONEY_EMAIL = "send.money@pmb.com";
    

    /**
     * Empty constructor.
     */
    private PmbConstants() {
    }
}
