/**
 * @author Edrine Frances
 * @author Leonne Matthew Dayao
 * @author Rayna Gulifardo
 * 2CSC - CICS - University of Santo Tomas
 * 
 * Guest window.
 * Receives result set & adds, updates, and deletes records.
 */

/*
TODO:
- Combine GUI, error dialogues into windows
- Something else idk it's 8 pm i'm hungover w/out alcohol or a headache, just the brainrot
- fafda
*/

package test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

class AdminWindow /*extends JFrame*/ {
    
    private final String LOGOUT_KEY = "logout";
    private final String ADD_KEY = "add";
    private final String UPDATE_KEY = "update";
    private final String DELETE_KEY = "delete";
    private Scanner scanCmd = new Scanner(System.in);
    
    AdminWindow(String loginUname, String loginPword, Connection con, ResultSet rs) throws SQLException {
            // Guest Window
            // Create and Execute the Statement
            System.out.println("____________________");
            
            // Retrieve the ResultSet
            while (rs.next()) {
                System.out.println("Email: " + rs.getString("Email").trim());
                System.out.println("Pword: " + rs.getString("Password").trim());
                System.out.println("Role: " + rs.getString("UserRole").trim());
                System.out.println("");
                System.out.println();
            }
            
            String cmdInput = "";
            
            while (!cmdInput.matches(LOGOUT_KEY)){
                String instructions = 
                        "Type " + ADD_KEY + " to add a record. " + 
                        "Type " + UPDATE_KEY + " to add a record. " + 
                        "Type " + DELETE_KEY + " to add a record. " + 
                        "Type " + LOGOUT_KEY + " to logout. ";
                System.out.println(instructions);
                
                cmdInput = scanCmd.nextLine().trim();
                switch (cmdInput) {
                    case ADD_KEY:
                        AddWindow add = new AddWindow(con);
                        break;
                    case UPDATE_KEY:
                        UpdateWindow update = new UpdateWindow(con, loginUname);
                        break;
                    case DELETE_KEY:
                        DeleteWindow delete = new DeleteWindow(con, loginUname);
                        break;
                    case LOGOUT_KEY:
                        break;
                    default:
                        break;
                }
            }
    }
    
    private String setPword() {
        System.out.println("Set password:");
        String error = "a", pword = "", confirmPword;
        while(!error.equals("")) {
            pword = scanCmd.nextLine();
            error = validatePword(pword);
            if (!error.equals("")) {
                System.out.println(error);
                System.out.println("Please input again.");
            }
        }
        System.out.println("Confirm password: ");
        boolean confirmPwordFlag;
        do {
            confirmPword = scanCmd.nextLine();
            confirmPwordFlag = confirmPword.matches(pword);
            if (!confirmPwordFlag)
                System.out.println("Password incorrect. Please input again:");
        }
        while (!confirmPwordFlag);
        return pword;
    }
    
    private String validatePword(String pword) {
        //Pattern ucase = Pattern.compile("[A-Z]+");
        String error = "";
        if (pword.equals("") || pword == null)
            return "Password must not be empty.\n";
        if (!pword.matches(".*[A-Z]+.*"))
            error += "Password must have at least 1 uppercase letter.\n";
        if (!pword.matches(".*[a-z]+.*"))
            error += "Password must have at least 1 lowercase letter.\n";
        if (!pword.matches(".*[0-9]+.*"))
            error += "Password must have at least 1 number.\n";
        if (!pword.matches(".*[^A-Za-z0-9]+.*"))
            error += "Password must have at least 1 special character.\n";
        return error;
    }
    
    private void printError(SQLException e, Object key) {
        System.out.println("There was a problem with the insertion. "
                                    + "Please type in " + ADD_KEY + " again, "
                                            + "or another instruction.");
                            System.out.println("Stack Trace:");
                            e.printStackTrace();
    }
    
    /**
     * Admin functions for add, update, and delete.
     * As these will extend JFrame and become quite complicated, these may need their own Java file soon enough.
     * I want to puke
     */
    
    private class AddWindow {
        private final String addRecord = "INSERT INTO users " +
                                "(Email, Password, UserRole) " + 
                                "VALUES (?, ?, ?)";
        AddWindow(Connection con) {
            try {
                PreparedStatement psAdd = con.prepareStatement(addRecord);

                // Set loginUname
                System.out.println("Set email:");
                psAdd.setString(1, scanCmd.nextLine()); // TODO check first if loginUname exists, if possible.

                /*reparedStatement psAdd = con.prepareStatement(addRecord);
                EMAIL SPECS:
                1. If string does not exactly have one '@', output error.
                2. If string has other special characters, output error.
                3. 
                */

                // Set password
                psAdd.setString(2, setPword());

                // Set user role
                System.out.println("Set user role:");
                psAdd.setString(3, scanCmd.nextLine().trim().toUpperCase());
                psAdd.executeUpdate();
            }
            catch (SQLException sqle) {
                System.out.println("There was a problem with the insertion. "
                        + "Please type in " + ADD_KEY + " again, "
                                + "or another instruction.");
                System.out.println("Stack Trace:");
                sqle.printStackTrace();
            }
        }
    }
    
    private class UpdateWindow {
        UpdateWindow(Connection con, String loginUname) {
            // NOTE: No need to verify email. When updating, only the list can be selected.
            try {
                String updateRecord = "UPDATE users " +
                    "SET Password = ?, UserRole = ? " + 
                    "WHERE Email = ?";
                String updateRecordSelf = "UPDATE users " +
                    "SET Password = ? " + 
                    "WHERE Email = ?";
                PreparedStatement psUpdate;

                // Select loginUname to edit:
                System.out.println("Input email: ");
                String updateEmail = scanCmd.nextLine();
                if (updateEmail.equals(loginUname)) {
                    System.out.println("Record is currently logged-in user");
                    psUpdate = con.prepareStatement(updateRecordSelf);
                    psUpdate.setString(1, setPword());
                    psUpdate.setString(2, updateEmail);
                }
                else {
                    System.out.println("Record is NOT currently logged-in user");
                    psUpdate = con.prepareStatement(updateRecord);
                    psUpdate.setString(1, setPword());
                    System.out.println("Set user role:");
                    psUpdate.setString(2, scanCmd.nextLine());
                    psUpdate.setString(3, updateEmail);
                }
                psUpdate.executeUpdate();
            }
            catch (SQLException sqle) {
                printError(sqle, UPDATE_KEY);
            }
        }
    }
    
    private class DeleteWindow {
        DeleteWindow(Connection con, String loginUname) {
            try {
                String deleteRecord = "DELETE FROM users WHERE Email = ?";
                PreparedStatement psDelete = con.prepareStatement(deleteRecord);

                // Select loginUname to edit:
                String deleteEmail;
                boolean sameEmailFlag;

                System.out.println("Input the email to delete:");
                do {
                    deleteEmail = scanCmd.nextLine();
                    sameEmailFlag = deleteEmail.equals(loginUname);
                    if (sameEmailFlag) {
                        System.out.println("Email is same as current login email. Please input again:");
                    }
                }
                while (sameEmailFlag);

                //System.out.println("psDelete is " + psDelete.toString());
                psDelete.setString(1, deleteEmail);
                psDelete.executeUpdate();
            }
            catch (SQLException sqle) {
                printError(sqle, DELETE_KEY);
            }
        }
    }
}