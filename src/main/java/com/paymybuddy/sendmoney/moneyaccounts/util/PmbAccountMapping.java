/**
 * 
 */
package com.paymybuddy.sendmoney.moneyaccounts.util;

import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;

/**
 * @author Thierry SCHREINER
 *
 */
@Component
public class PmbAccountMapping {

    public PmbAccountDTO mapPmbAccountToDTO(final PmbAccount pmbAccount) {
        PmbAccountDTO myPmbAccountDTO = new PmbAccountDTO();
        myPmbAccountDTO.setPmbAccountNumber(pmbAccount.getPmbAccountNumber());
        myPmbAccountDTO.setOwnerMail(pmbAccount.getOwner().getEmail());
        myPmbAccountDTO.setConnections(new TreeSet<String>());
        for (PmbAccount connection : pmbAccount.getConnections()) {
            myPmbAccountDTO.addConnection(connection.getOwner().getEmail());
        }
        
        return myPmbAccountDTO;
    }
}
