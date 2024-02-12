/**
 * @author Edrine Frances
 * @author Leonne Matthew Dayao
 * @author Rayna Gulifardo 2CSC - CICS - University of Santo Tomas
 *
 * Test statements to build MP1 functionality. Code will eventually be modified
 * wherever needed.
 */
package test;
// import packages
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TestStatement
{
    // THESE STRINGS ARE USED UNIVERSALLY BY ALL WINDOWS.
    // DO NOT REMOVE
    private static String uname, pword, urole;
    public static void main(String[] args)
    {
        TestStatement ts = new TestStatement();
        ts.startApp();
    }
    public void startApp()
    {
        try
        {
            /*
                LOGIN WINDOW
             */
            // Scanner Input (for testing only)
            // Scanner scanUname = new Scanner(System.in);
            // Scanner scanPword = new Scanner(System.in);
            // Scanner scanCmd = new Scanner(System.in);
            // String loginUname, loginPword, loginUrole;
            // Verification
            /*while (true)
            {
                System.out.println("Input username & password to continue:");
                loginUname = scanUname.nextLine();
                loginPword = scanPword.nextLine();
                // Verify W/ Server
                String verQuery = "SELECT * FROM USERS "
                        + "WHERE Email = ?";
                PreparedStatement accPs = con.prepareStatement(verQuery);
                accPs.setString(1, loginUname);
                ResultSet accRs = accPs.executeQuery();
                if (accRs.next())
                { // next() method returns false if no corresp. entry is found.
                    if (accRs.getString("Password").matches(loginPword))
                    {
                        System.out.println("Authentication passed.");
                        loginUrole = accRs.getString("UserRole");
                        accRs.close();
                        accPs.close();
                        break;
                    }
                }
                // "INCORRECT" Error Window
                System.out.println("Incorrect username and/or password.");
                System.out.println(3 - c++ + " tries left.");
                // OK Button that closes window
                // "FINISHED" Error Window
                if (c >= 3)
                {
                    System.out.println("Sorry, you have reached the limit of 3 tries. Goodbye!");
                    accRs.close();
                    accPs.close();
                    con.close();
                    System.exit(0);
                }
            }*/
            /**
             * TODO: - Separate most of if not all of this code from main(). -
             * Implement GUI once available.
             *
             * DUE DATE: February 16, Friday
             */
            try ( Connection con = makeConnection())
            {
                LoginDialog ld = new LoginDialog(con);
                ld.startApp();
                String query = "SELECT * FROM USERS ORDER BY Email";
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                switch (urole.toUpperCase())
                {
                    case "ADMIN":
                        AdminWindow aw = new AdminWindow(uname, pword, con, rs);
                        break;
                    case "GUEST":
                        GuestUI gui = new GuestUI(rs);
                        break;
                }
                rs.close();
                ps.close();
            }
        } catch (SQLNonTransientConnectionException ce)
        {
            JOptionPane.showMessageDialog(null, "Server is currently down. Please check with the administrator.");
            ce.printStackTrace();
        } catch (SQLException | ClassNotFoundException sqle)
        {
            JOptionPane.showMessageDialog(null, "Sorry, an error occurred. The program will now close.");
            sqle.printStackTrace();
        }
    }
    private static Connection makeConnection() throws SQLException, ClassNotFoundException
    {
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
        return con;
    }
    private ResultSet fetchDatabase(Connection con) throws SQLException
    {
        try ( PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS ORDER BY Email"))
        {
            ResultSet rs = ps.executeQuery();
            return rs;
        }
    }

    private class LoginDialog extends JDialog implements ActionListener, KeyListener
    {
        Connection con;
        Container c;
        JLabel userLabel, passLabel;
        JTextField userField;
        JPasswordField passField;
        JButton loginButton;
        private int count = 0;
        LoginDialog(Connection con)
        {
            this.con = con;
            setTitle("Login Form");
            setSize(375, 275);
            setLocationRelativeTo(null);
            setResizable(false);
            setModal(true);
            c = getContentPane();
            userLabel = new JLabel("Username");
            passLabel = new JLabel("Password");
            userLabel.setBounds(40, 50, 100, 30);
            passLabel.setBounds(40, 100, 100, 30);
            userField = new JTextField();
            userField.setBounds(120, 50, 200, 30);
            passField = new JPasswordField();
            passField.setBounds(120, 100, 200, 30);
            loginButton = new JButton("Login");
            loginButton.setBounds(140, 150, 70, 30);
            // TODO
            // - 3-counter limit w/ corresp. errors, login error window, connection error window/s(?)
            // - Guest & admin windows (see MP1 .xsl guidelines)
            // - Some other stuff perhaps being missed
        }
        public void startApp()
        {
            c.setLayout(null);
            c.add(userLabel);
            c.add(passLabel);
            c.add(userField);
            c.add(passField);
            c.add(loginButton);
            loginButton.addActionListener(this);
            passField.addKeyListener(this);
            addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    dispose();
                    try
                    {
                        con.close();
                    } catch (SQLException sqle)
                    {
                        System.out.println("Unexpected error after exiting app! Please consult.");
                        sqle.printStackTrace();
                    } finally {
                        System.exit(0);
                    }
                }
            });
            setVisible(true);
        }
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == loginButton)
            {
                loginRequest();
            }
        }
        @Override
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                loginRequest();
            }
        }
        private void loginRequest()
        {
            try
            {
                // Verify W/ Server
                String VER_QUERY = "SELECT * FROM USERS "
                        + "WHERE Email = ?";
                uname = userField.getText(); // REFERS TO OUTER CLASS/MAIN OBJECT.
                pword = passField.getText(); // REFERS TO OUTER CLASS/MAIN OBJECT.
                PreparedStatement accPs = con.prepareStatement(VER_QUERY);
                accPs.setString(1, uname);
                ResultSet accRs = accPs.executeQuery();
                if (accRs.next())
                { // next() method returns false if no corresp. entry is found.
                    if (accRs.getString("Password").matches(pword))
                    {
                        urole = accRs.getString("UserRole"); // REFERS TO OUTER CLASS/MAIN OBJECT.
                        JOptionPane.showMessageDialog(null, "Successfully logged in.");
                        accRs.close();
                        accPs.close();
                        this.dispose();
                        return;
                    }
                }
                // "INCORRECT" Error Window
                if (count < 3)
                {
                    JOptionPane.showMessageDialog(null,
                            "Incorrect username and/or password.\n" + (3 - count++) + " tries left.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } // "FINISHED" Error Window
                else
                {
                    JOptionPane.showMessageDialog(null, "Sorry, you have reached the limit of 3 tries. Good bye!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    accRs.close();
                    accPs.close();
                    con.close();
                    System.exit(0);
                }
            } catch (SQLException sqle)
            {
                JOptionPane.showMessageDialog(null, "There was an error in the database loading. Program will now close",
                        "Error", JOptionPane.ERROR_MESSAGE);
                sqle.printStackTrace();
                System.exit(0);
            }
        }
        @Override
        public void keyTyped(KeyEvent e)
        {
        }
        @Override
        public void keyReleased(KeyEvent e)
        {
        }
    }
}
