/**
 * @author Edrine Frances
 * @author Leonne Matthew Dayao
 * @author Rayna Gulifardo
 * 2CSC - CICS - University of Santo Tomas
 * 
 * Java sample code to show JavaDB functionality.
 * Elements to be copied/modified as needed.
 */

package test;

// import packages
import java.sql.*;

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

            // Create and Execute the Statement
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM USERS ORDER BY Email";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Executed Query: " + query);

            // Retrieve the ResultSet
            System.out.println("Processing The Result Set: ");
            while (rs.next()) {
                System.out.println("Email: " + rs.getString("Email").trim());
                System.out.println("Pword: " + rs.getString("Password").trim());
                System.out.println("Role: " + rs.getString("UserRole").trim());
                System.out.println("");
                System.out.println();
            }

            // Close the connection
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException | ClassNotFoundException sqle) {
            sqle.printStackTrace();
        }
    }
}