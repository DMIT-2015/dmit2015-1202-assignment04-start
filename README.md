# Project dmit2015/dmit2015-assignment04-start

In this assignment you are going to convert the starter project that contains a non-secure single-user Bill and Bill Payment application
to a secure multi-user application where register users can manage their own Bill and Bill Payment.

The application will support two types of roles: REGISTER_USER and ADMIN.
The REGISTER_USER role is allowed to manage their own Bill and BillPayment.
The ADMIN role is allowed to manage Bill and BillPayment for all users, create and list users, and access the h2-console.

This assignment assumes you are using a Database identity store. 
If you choose to use an LDAP identity store using the provided Windows Server VM you can replace the roles REGISTER_USER with Sales and ADMIN with IT.

Requirements:
1. If choose to use a Database identity store then add the following list of users to your database.

    username: larry@3stooges.com
    password: Password2015
    role groups: ADMIN

    username: curly@3stooges.com
    Password: Password2015
    role groups: REGISTER_USER
   
    username: moe@3stooges.com
    password: Password2015
    role groups: REGISTER_USER
   
2. Configure the project to use a custom form authentication mechanism.

3. Configure the project with links on each page for the user to login and logout.

4. Set a security constraint to restrict access to all URLs to manage Bill/BillPayment to the role REGISTER_USER or ADMIN.

5. Set a security constraint to restrict access to the URL to the h2-console DEVELOPER.

6. Modify the Bill entity to include the authenticated username.

7. Restrict access to all methods in BillRepository.java and BillPaymentRepository.java to authenticated users with the role REGISTER_USER or ADMIN.

8. Modify the create method in BillRepository.java to set the username of the Bill.

9. Modify the findAll method in BillRepository.java to work as follows.
   If the role of the caller is ADMIN return a list of all Bill entity.
   If the role of the caller is REGISTER_USER return a list of all Bill entity with the username of the caller.
   If the role of the caller is any other role return an empty list of Bill entity.
   
10. Configure your project to display custom error pages for error codes 401, 403, 404, and 500.

11. Configure your project to transport all URLs over HTTPS instead of HTTP.



