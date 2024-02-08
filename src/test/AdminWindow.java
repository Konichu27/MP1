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
INCOMPLETE
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
    
    AdminWindow(Connection con, ResultSet rs) throws SQLException {
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
                        "Type " + ADD_KEY + " to add a record." + 
                        "Type " + UPDATE_KEY + " to add a record." + 
                        "Type " + DELETE_KEY + " to add a record." + 
                        "Type " + LOGOUT_KEY + " to logout.";
                System.out.println(instructions);
                
                cmdInput = scanCmd.nextLine().trim();
                switch (cmdInput) {
                    case ADD_KEY: // WORK-IN-PROGRESS!!!!
                        String addRecord = "INSERT INTO users " +
                                "(Email, Password, UserRole) " + 
                                "VALUES (?, ?, ?)";
                        try {
                            PreparedStatement psAdd = con.prepareStatement(addRecord);
                            
                            // Set email
                            System.out.println("Set email:");
                            psAdd.setString(1, scanCmd.nextLine()); // TODO check first if email exists, if possible.
                            
                            // Set password
                            System.out.println("Set password:");
                            String error = "a", newPword;
                            
                            while(!error.equals("")) {
                                newPword = scanCmd.nextLine();
                                error = validatePword(newPword);
                                System.out.println("!error.equals(\"\") is " + !error.equals(""));
                                if (!error.equals("")) {
                                    System.out.println(error);
                                    System.out.println("Please input again.");
                            }
                        }
                            psAdd.setString(2, scanCmd.nextLine()); // TODO add password verification. This will be a separate function in the future.
                            
                            // Set user role
                            System.out.println("Set user role:");
                            psAdd.setString(3, scanCmd.nextLine().trim().toUpperCase()); // TODO check if GUEST or ADMIN only.
                            psAdd.executeUpdate();
                        }
                        catch (SQLException sqle) {
                            System.out.println("There was a problem with the insertion. "
                                    + "Please type in " + ADD_KEY + " again, "
                                            + "or another instruction.");
                            System.out.println("Stack Trace:");
                            sqle.printStackTrace();
                        }
                        break;
                    case UPDATE_KEY:
                        break;
                    case DELETE_KEY:
                        break;
                    case LOGOUT_KEY:
                        break;
                    default:
                        break;
                }
            }
    }
    
    private String validatePword(String pword) {
        //Pattern ucase = Pattern.compile("[A-Z]+");
        String error = "";
        System.out.println("New password is " + pword);
        if (!pword.matches("[A-Z]"))
            error += "Password must have at least 1 uppercase letter.\n";
        if (!pword.matches("[a-z]"))
            error += "Password must have at least 1 lowercase letter.\n";
        if (!pword.matches("[0-9]"))
            error += "Password must have at least 1 number.\n";
        if (!pword.matches("[^A-Za-z0-9]"))
            error += "Password must have at least 1 special character.\n";
        return error;
    }
}