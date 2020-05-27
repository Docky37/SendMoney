#Pay My Buddy

###Infos
author: Thierry 'Docky' SCHREINER - DA Java student - Open ClassRooms

mentored by: Yann 'Monsieur Plus' IRRILO

release date: 27/05/2020




### Conceptual Model


<div hidden>

	```
	@startuml firstDiagram
    class Buddy  {
        -firstName
        -lastName
        -eMail
        -- encrypted --
        -password
    }
    Left to right direction
    Buddy "many" o-- "   many" Buddy : < has connection
    class PmbAccount  {
        -accountNumber
        -accountOwner
        -accountBalance
    }
    Buddy "1  " --  "1" PmbAccount : > has
    class BankAccount {
        -- encrypted --
        -iban
        -swift
    }
    Buddy "1" --  "1  " BankAccount : > has
     
    class Transfer {
        -operationDate
        -description
        -fromPmbAccount
        -toPMBAccount
        -bankAccount
        -amount
        -fee
    }
    PmbAccount "0..1" --  "*" Transfer
    PmbAccount " 0..1" --  "*" Transfer
    BankAccount "0..1  " --  "*" Transfer
    class Billing {
        -date
        -fee
    }
    Transfer "1" *--> "0..1" Billing
    
	@enduml
	```
	
</div>

![](firstDiagram.png)	

