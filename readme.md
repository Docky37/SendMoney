#Pay My Buddy - v1.1


###Infos

author: Thierry 'Docky' SCHREINER - DA Java student - Open ClassRooms

mentored by: Yann 'Monsieur Plus' IRRILO

release date: July, 17th 2020


### Content

The v1.1 fix the credentials kill problem after a SQL export/import due to a character encoding trouble by replacing password VARCHAR type by BINARY(60) type in buddy table.

Previous version content:
- Authentication that provides a Json Web Token (activity period 20 minutes).
- GET - http://localhost:8080/welcome
- Sign-up endpoint -> POST - http://localhost:8080/registration
- Bank account creation -> POST - http://localhost:8080/bank-account
- Add a connection -> POST - http://localhost:8080/connection
- See list of connections -> GET - http://localhost:8080/connection
- Remove a connection -> DELETE - http://localhost:8080/connection/{email}
- v0.4 release adds the Money transfer functionality with a POST request
feature_getTransfer (/sendMoney), using an orderDTO:
 
    {
        "beneficiary":"Daniel.Craig@JamesBond.fr",
        "amount":350
    }

- v1.0 release adds Money deposit and withdrawal functionalities with the same
orderDTO. Deposit POST request on /pmb-adm/deposit need an admin logged user.



### Database

The file data.sql (in sendmoney/src/main/resources) contains sql instructions 
to create both prod and test databases, their tables and some data.

Application uses mySQL 8.0 with user 'root' and password 'rootroot'.


 

### Conceptual Model


<!--

	```
	@startuml firstDiagram
    class Buddy  {
        -firstName
        -lastName
        -eMail
        -- encrypted --
        -password
    }
    class PmbAccount  {
        -accountNumber
        -accountBalance
    }
    Left to right direction
    PmbAccount "many" o-- "  many" PmbAccount : < has connection
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
	
-->

![](firstDiagram.png)	




### Logical Model


<!--

    ```
    @startuml logicalDiagram
    class Buddy  {
        -firstName: String
        -lastName: String
        -eMail: String
        -- encrypted --
        -password: String
    }
    class PmbAccount  {
        -accountNumber: integer
        -accountOwner: integer
        -accountBalance: double
    }
    Left to right direction
    PmbAccount "many" o-- "many " PmbAccount : < has connection
    Buddy "1  " --  "1" PmbAccount : > has
    class BankAccount {
        -- encrypted --
        -iban: String
        -swift: String
    }
    Buddy "1" --  "1  " BankAccount : > has
     
    class Transfer {
        -valueDate: LocalDateTime
        -description: String
        -fromPmbAccount: long
        -toPMBAccount: long
        -bankAccount: long
        -amount: double
        -fee: double
    }
    PmbAccount "0..1" --  "*" Transfer : < from account
    PmbAccount " 0..1" --  "*" Transfer : < to account
    BankAccount "0..1  " --  "*" Transfer : < from/to bank account

    
    @enduml
    ```
    
-->

![](logicalDiagram.png)   



