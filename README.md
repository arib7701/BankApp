-------------------------------- LOCAL SET UP -------------------------
// DATABASE SET UP

1. Create a database schema named bankapp
2. Use the bankapp.sql (in the docs folder) to build tables in the database. Two customers and three accounts are inserted but you can remove those lines.

// BACK SET UP

3. In the src/main/resources/application.properties, change your username and password to access MySQL
4. In src/main/java/com/bankapp, run the application BankAppApplication.java
5. The api run on port localhost:8181 --> your back is ready

// FRONT SET UP
6. In the command line, go to the client folder
7. Run npm install and run ng serve
8. Go to localhost:4200 for client side
9. Go to localhost:4200/admin for admin side 

-------------------------------- BASIC USAGE -------------------------

// ADMIN SIDE
1. Use any valid email and any password to log into the app (no data saved for admin in DB)
2. Create a new client with name and email
3. Add account(s) to newly created client

// CLIENT SIDE
1. Use email created for client in admin side, use any password to log into the app
2. Check accounts
3. Add/Remove/Transfer money

-------------------------------- WEB ADDRESS -------------------------

Application available at: 
https://progressivebankapp.herokuapp.com/ for client side and 
https://progressivebankapp.herokuapp.com/admin for admin side.

