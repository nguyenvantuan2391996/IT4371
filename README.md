# IT4371
                                                Distributed System
 - IDE : Netbean, Java.
 - Database : MySQL.
 - Socket, Transaction, Data-centric Consistency Model, Multithreading.
 - Author : Student at Ha Noi University of Science and Technology
 
                                   **********************************************
 
 - Import bank_vn, bank_us, bank_uk to database : MySQL.
 - Run ServerVN.java, ServerUS.java, ServerUK.java. Servers always wait to receive requests from Client. After it's receieved, it process Client's requests and reponse to Client.
 - Run Main.java to work like as User and choose Server that you want to work.
 - Run MainAdmin.java to work like as Admin and choose Server that you want to work.
 
 
                                   **********************************************
 
 Create a simple distributed banking system. The system will consist of two programs that serve as services. The first service represents the bank. This bank has a number of accounts. Accounts can be created for clients (with any initial deposit value not negative) and can be canceled. In addition, accounts can be added to deposits. With the account balance is positive, the user can withdraw money.
 
 Customer representation services provide access to bank functions such as deposit balance queries, plus deposits and withdrawals. Note an error occurs if the bank tries to create an existing account or cancel an account that does not exist. This system will be like a bank in fact, will not withdraw money if there is not enough money in the account.
 
 The error is displayed in the client side program in some way (for example, the program will instruct the user why an operation can not be).
 
 The system uses socket communication between client (client) and bank (server).
 
 The test model of MyInternetBanking activities is as follows:
 
      1. The system acts as an account creation script, sending money, reporting bugs etc. as described above.
      2. Same as the main cardholder and supplementary cardholder model. An account can have multiple customers at the same time.
      3. To ensure data security, backup banks store data in two different data warehouses.
      4. Two users with the same withdrawal account and data are recorded in two different repositories.
      5. The MyInternetBanking service needs to ensure consistency in the data at the two repositories.
