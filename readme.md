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
        -valueDate
        -description
        -amount
        -fee
    }
    PmbAccount "0..1" --  "*" Transfer : < from account
    PmbAccount " 0..1" --  "*" Transfer : < to account
    BankAccount "0..1  " --  "*" Transfer : < from/to bank account
    
	@enduml
	```
	
</div>

![](firstDiagram.png)	




### Logical Model


<div hidden>

    ```
    @startuml logicalDiagram
    class Buddy  {
        -firstName: String
        -lastName: String
        -eMail: String
        -- encrypted --
        -password: String
    }
    Left to right direction
    Buddy "many" o-- "   many" Buddy : < has connection
    class PmbAccount  {
        -accountNumber: Integer
        -accountOwner: Integer
        -accountBalance: BigDecimal
    }
    Buddy "1  " --  "1" PmbAccount : > has
    class BankAccount {
        -- encrypted --
        -iban: Integer
        -swift: String
    }
    Buddy "1" --  "1  " BankAccount : > has
     
    class Transfer {
        -valueDate: LocalDateTime
        -description: String
        -fromPmbAccount: Integer
        -toPMBAccount: Integer
        -bankAccount: Integer
        -amount: BigDecimal
        -fee: BigDecimal
    }
    PmbAccount "0..1" --  "*" Transfer : < from account
    PmbAccount " 0..1" --  "*" Transfer : < to account
    BankAccount "0..1  " --  "*" Transfer : < from/to bank account
    class Billing {
        -valueDate: LocalDateTime
        -fee: BigDecimal
    }
    Transfer "1" *--> "0..1" Billing
    
    @enduml
    ```
    
</div>

![](logicalDiagram.png)   



