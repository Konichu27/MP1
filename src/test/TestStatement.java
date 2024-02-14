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
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

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
        System.out.println("Test");
        try
        {
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
                    switch (urole.toUpperCase())
                    {
                        case "ADMIN":
                            new AdminUI(con, uname);
                            break;
                        case "GUEST":
                            new GuestUI(con);
                            break;
                    }
            }
        } catch (SQLNonTransientConnectionException ce)
        {
            JOptionPane.showMessageDialog(null, "Server is currently down. Please check with the administrator, then launch this app again.", "Error Screen", JOptionPane.ERROR_MESSAGE);
            ce.printStackTrace();
        } catch (SQLException | ClassNotFoundException sqle)
        {
            JOptionPane.showMessageDialog(null, "Sorry, an error occurred. The program will now close.", "Error Screen", JOptionPane.ERROR_MESSAGE);
            sqle.printStackTrace();
        }
    }
    private Connection makeConnection() throws SQLException, ClassNotFoundException
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

    private class LoginDialog extends JDialog implements ActionListener, KeyListener
    {
        private String adminUname;
        private Connection con;
        private Container c;
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
                    try
                    {
                        con.close();
                    } catch (SQLException sqle)
                    {
                        System.out.println("Unexpected error after exiting app! Please consult.");
                        sqle.printStackTrace();
                    } finally
                    {
                        System.exit(0);
                    }
                }
            });
            setVisible(true);
        }
        @Override
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
                PreparedStatement psAcc = con.prepareStatement(VER_QUERY);
                psAcc.setString(1, uname);
                ResultSet rsAcc = psAcc.executeQuery();
                if (rsAcc.next())
                { // next() method returns false if no corresp. entry is found.
                    if (rsAcc.getString("Password").equals(pword))
                    {
                        urole = rsAcc.getString("UserRole"); // REFERS TO OUTER CLASS/MAIN OBJECT.
                        JOptionPane.showMessageDialog(null, "Successfully logged in.");
                        rsAcc.close();
                        psAcc.close();
                        dispose();
                        return;
                    }
                }
                // "INCORRECT" Error Window
                if (count < 3)
                {
                    JOptionPane.showMessageDialog(null,
                            "Incorrect Username / Password",
                            "Error Screen", JOptionPane.ERROR_MESSAGE);
                    count++;
                } // "FINISHED" Error Window
                else
                {
                    JOptionPane.showMessageDialog(null, "Sorry, you have reached the limit of 3 tries, good bye!",
                            "Error Screen", JOptionPane.ERROR_MESSAGE);
                    rsAcc.close();
                    psAcc.close();
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
