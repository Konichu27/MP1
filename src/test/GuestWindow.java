/**
 * @author Edrine Frances
 * @author Leonne Matthew Dayao
 * @author Rayna Gulifardo
 * 2CSC - CICS - University of Santo Tomas
 * 
 * Guest window.
 * Receives result set & 
 */

package test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class GuestWindow /*extends JFrame*/ {
    
    private String logoutKey = "logout";
    private Scanner scanCmd = new Scanner(System.in);
    
    GuestWindow(ResultSet rs) throws SQLException {
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
            
            while (!cmdInput.matches(logoutKey)){
                System.out.println("Type " + logoutKey + " to logout.");
                cmdInput = scanCmd.nextLine().trim();
            }
    }
}