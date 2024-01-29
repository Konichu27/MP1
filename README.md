# How to use GitHub
**Git Terminologies**
- Git - program used by developers to share, update, & delete code
- Repository/Repo - storage of code
- Remote repository - networked repo. (like this one here in GitHub)
- Commit - save changes thru Git locally
- Push - save changes to remote repository online
- Clone - download full copy of repository
- Pull - update Git copy in computer directly to latest copy
- Fetch - download changes of existing repository (probably not needed for most of our purposes)

**How to set up GitHub**

1. Sign up for an account, PM w/ username for invitation
2. Download & install git.exe: https://git-scm.com/downloads
3. Clone repository following these instructions: https://netbeans.apache.org/tutorial/main/kb/docs/ide/git/#_cloning_a_git_repository

**IMPORTANT:** Make sure to make changes in the `main` branch, not `master` or any other.

   Please also consult the group before pushing changes.
   
- To commit & push, or pull, follow these instructions:
  - https://mauricemuteti2015.medium.com/how-to-upload-push-add-netbeans-java-project-to-github-d3c098922663 (3-11 primarily)
  - https://netbeans.apache.org/tutorial/main/kb/docs/ide/git/#_pushing

For issues, PM in Messenger/Discord (even *I* don't fully understand everything, sooooo)

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
