/**
 * @author Edrine Frances
 * @author Leonne Matthew Dayao
 * @author Rayna Gulifardo
 * 2CSC - CICS - University of Santo Tomas
 * 
 * Test statements to build MP1 functionality.
 * Code will eventually be modified wherever needed.
 */

package test;

// import packages
import java.sql.*;
import java.util.Scanner;

public class TestStatement {

    public static void main(String[] args) {
        try {
            // Load Driver
            String driver = "org.apache.derby.jdbc.ClientDriver";
            Class.forName(driver);
            System.out.println("Loaded Driver: " + driver);

            // Establish Connection
            String url = "jdbc:derby://localhost:1527/LoginDB";
            String username = "app";
            String password = "app";
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to: " + url);
            
            // Scanner Input (for testing only)
            Scanner unameScan = new Scanner(System.in);
            Scanner pwordScan = new Scanner(System.in);
            Scanner cmdScan = new Scanner(System.in);
            String unameInput, pwordInput, cmdInput = "";
            int c = 0;
            
            // Verification
            while (true) {
                System.out.println("Input username & password to continue:");
                unameInput = unameScan.nextLine();
                pwordInput = pwordScan.nextLine();
                
                // Verify W/ Server
                String verQuery = "SELECT * FROM USERS "
                        + "WHERE Email = ?";
                PreparedStatement accPs = con.prepareStatement(verQuery);
                accPs.setString(1, unameInput);
                ResultSet accRs = accPs.executeQuery();
                if (accRs.next()) { // next() method returns false if no corresp. entry is found.
                    if (accRs.getString("Password").matches(pwordInput)) {
                        System.out.println("Authentication passed.");
                        accRs.close();
                        accPs.close();
                        break;
                    }
                }
                System.out.println("Incorrect username and/or password.");
                c++;
                System.out.println(3-c + " tries left.");
                
                if (c >= 3) {
                    System.out.println("Sorry, you have reached the limit of 3 tries. Goodbye!");
                    accRs.close();
                    accPs.close();
                    con.close();
                    System.exit(0);
                }
            }
            
            // Create and Execute the Statement
            String query = "SELECT * FROM USERS ORDER BY Email";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("____________________");

            // Retrieve the ResultSet
            displayUsers(rs);
            
            /**
             * TODO:
             * - Implement separate Guest & Admin codes/windows.
             * - Implement Insert, Update, Delete, etc.
             * - Separate most of if not all of this code from main().
             * - Implement GUI once available.
             * 
             * DUE DATE: February 16, next next Friday
             */
            
            while (!cmdInput.matches("exit")){
                System.out.println("Type 'exit' to end program.");
                cmdInput = cmdScan.nextLine().trim();
            }

            // Close the connection
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException | ClassNotFoundException sqle) {
            System.out.println("Sorry, an error occurred. The program will now close.");
            sqle.printStackTrace();
        }
    }
    
    private static void displayUsers(ResultSet rs) throws SQLException {
            while (rs.next()) {
                System.out.println("Email: " + rs.getString("Email").trim());
                System.out.println("Pword: " + rs.getString("Password").trim());
                System.out.println("Role: " + rs.getString("UserRole").trim());
                System.out.println("");
                System.out.println();
            }
    }
}