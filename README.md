# Java project details
Project name: `MP1_2CSC_Dayao_Esguerra_Gulifardo`

Package name: `test`

Database name: `LoginDB`

Username: `app`

Password: `app`

**NOTE:** In order for the project to work properly, you need to insert derbyclient.jar into the project.
1. Install the derbyclient.jar in the class repo/this repo (there is one already in NetBeans but it will not work)
2. Open the NetBeans project in the Projects menu
3. Right-click on Libraries and click Add Library...
4. Insert the .jar file

# Database details
Table name: `USERS`

Columns:
- `Email` (VARCHAR 30 UNIQUE NOT NULL)
- `Password` (VARCHAR 20 NOT NULL)
- `UserRole` (VARCHAR 10 NOT NULL)

# Business rules (WIP)
- Guests can only view records
- Admins can add data
- Admins can delete all others' data
- Admins cannot delete themselves
- Admins can update all others' data
- Admins can update their own email & password
- Admins cannot update their role
