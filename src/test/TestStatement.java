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
                    switch (urole.toUpperCase())
                    {
                        case "ADMIN":
                            new AdminUI(con);
                            break;
                        case "GUEST":
                            new GuestUI(con);
                            break;
                    }
            }
        } catch (SQLNonTransientConnectionException ce)
        {
            JOptionPane.showMessageDialog(null, "Server is currently down. Please check with the administrator, then launch this app again.");
            ce.printStackTrace();
        } catch (SQLException | ClassNotFoundException sqle)
        {
            JOptionPane.showMessageDialog(null, "Sorry, an error occurred. The program will now close.");
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
                    } finally
                    {
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

    /*class AdminUI extends JDialog
    {
        private Connection con;
        private ResultSet rs;
        private DefaultTableModel tableModel;
        private JTable recordsTable;
        private JButton addRecordButton;
        private JButton updateRecordButton;
        private JButton deleteRecordButton;
        private JButton logoutButton;
        public AdminUI(Connection con, ResultSet rs) throws SQLException
        {
            this.con = con;
            this.rs = rs;
            setTitle("Admin UI");
            setSize(600, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            tableModel = new DefaultTableModel();
            recordsTable = new JTable(tableModel);
            // layout the table
            recordsTable.setIntercellSpacing(new Dimension(15, 5));
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            recordsTable.setRowSorter(sorter);
            JTableHeader header = recordsTable.getTableHeader();
            header.setFont(new Font("Arial", Font.BOLD, 12));
            // header
            DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) recordsTable.getTableHeader().getDefaultRenderer();
            headerRenderer.setHorizontalAlignment(JLabel.CENTER);
            headerRenderer.setFont(new Font("Arial", Font.BOLD, 12));
            JScrollPane scrollPane = new JScrollPane(recordsTable);
            add(scrollPane, BorderLayout.CENTER);
            // buttons (no functions yet)
            addRecordButton = new JButton("Add Record");
            updateRecordButton = new JButton("Update Record");
            deleteRecordButton = new JButton("Delete Record");
            logoutButton = new JButton("Logout");
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(addRecordButton);
            buttonPanel.add(updateRecordButton);
            buttonPanel.add(deleteRecordButton);
            JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            logoutPanel.add(logoutButton);
            int marginSize = 10;  // adjust as needed
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
            logoutPanel.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
            add(buttonPanel, BorderLayout.NORTH);
            add(logoutPanel, BorderLayout.SOUTH);
            // LISTENERS
            addRecordButton.addActionListener((ActionEvent e) ->
            {
                new AddRecord();
            });
            logoutButton.addActionListener((ActionEvent e) ->
            {
                dispose();
            }); // exits the program when logout button is clicked
            recordsTable.setRowHeight(30);
            TableColumnModel columnModel = recordsTable.getColumnModel();
            for (int col = 0; col < columnModel.getColumnCount(); col++)
            {
                columnModel.getColumn(col).setPreferredWidth(150);
            }
            refreshData(tableModel);
            setVisible(true);
        }
        private void refreshData(DefaultTableModel newTableModel) throws SQLException
        {
            newTableModel.setRowCount(0);
            newTableModel.addColumn("Email");
            newTableModel.addColumn("Password");
            newTableModel.addColumn("User Role");
            while (rs.next())
            {
                String[] sampleData1 =
                {
                    rs.getString("Email").trim(), rs.getString("Password").trim(), rs.getString("UserRole").trim()
                };
                newTableModel.addRow(sampleData1);
            }
        }

        class AddRecord extends JDialog
        {
            private Container c;
            private JLabel userLabel, passLabel, confPLabel, roleLabel;
            private JTextField userField;
            private JPasswordField passField, confPass;
            private JButton add, cancel;
            private JRadioButton adminRadioButton, guestRadioButton;
            private ButtonGroup roleGroup;
            public AddRecord()
            {
                setTitle("Add Record");
                setSize(500, 400);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                c = getContentPane();
                c.setLayout(null);
                JPanel panel = new JPanel();
                panel.setLayout(null);
                panel.setBounds(10, 10, 465, 400);
                userLabel = new JLabel("Username");
                passLabel = new JLabel("Password");
                confPLabel = new JLabel("Confirm Password");
                roleLabel = new JLabel("Role");
                userLabel.setBounds(50, 50, 150, 30);
                passLabel.setBounds(50, 100, 150, 30);
                confPLabel.setBounds(50, 150, 150, 30);
                roleLabel.setBounds(50, 200, 150, 30);
                panel.add(userLabel);
                panel.add(passLabel);
                panel.add(confPLabel);
                panel.add(roleLabel);
                userField = new JTextField();
                userField.setBounds(220, 50, 200, 30);
                panel.add(userField);
                passField = new JPasswordField();
                passField.setBounds(220, 100, 200, 30);
                panel.add(passField);
                confPass = new JPasswordField();
                confPass.setBounds(220, 150, 200, 30);
                panel.add(confPass);
                adminRadioButton = new JRadioButton("Admin");
                adminRadioButton.setBounds(220, 200, 80, 30);
                panel.add(adminRadioButton);
                guestRadioButton = new JRadioButton("Guest");
                guestRadioButton.setBounds(300, 200, 80, 30);
                panel.add(guestRadioButton);
                roleGroup = new ButtonGroup();
                roleGroup.add(adminRadioButton);
                roleGroup.add(guestRadioButton);
                add = new JButton("Add");
                add.setBounds(120, 270, 90, 30);
                panel.add(add);
                add.addActionListener((ActionEvent e) ->
                {
                    try
                    {
                        addRecord();
                    } catch (SQLException sqle)
                    {
                        JOptionPane.showMessageDialog(null, "There was a problem while sending the request. \nPlease resubmit again (maybe edit or check with the administrator).", "Error", JOptionPane.ERROR_MESSAGE);
                        sqle.printStackTrace();
                    }
                });
                cancel = new JButton("Cancel");
                cancel.setBounds(250, 270, 90, 30);
                panel.add(cancel);
                cancel.addActionListener((ActionEvent e) ->
                {
                    dispose(); // exits the program when logout button is clicked
                });
                c.add(panel);
                setVisible(true);
            }
            private void addRecord() throws SQLException
            {
                String error = "";
                String uname = userField.getText();
                String pword = passField.getText();
                String confPword = confPass.getText();
                String urole = "";
                if (uname.equals("") || uname == null)
                {
                    error += "Username must not be empty.\n";
                }
                if (pword.equals("") || pword == null)
                {
                    error += "Password must not be empty.\n";
                } else
                {
                    if (!pword.matches(".*[A-Z]+.*"))
                    {
                        error += "Password must have at least 1 uppercase letter.\n";
                    }
                    if (!pword.matches(".*[a-z]+.*"))
                    {
                        error += "Password must have at least 1 lowercase letter.\n";
                    }
                    if (!pword.matches(".*[0-9]+.*"))
                    {
                        error += "Password must have at least 1 number.\n";
                    }
                    if (!pword.matches(".*[^A-Za-z0-9]+.*"))
                    {
                        error += "Password must have at least 1 special character.\n";
                    }
                }
                if (!pword.matches(confPword))
                {
                    error += "Passwords do not match.\n";
                }
                if (guestRadioButton.isSelected())
                {
                    urole = guestRadioButton.getText();
                } else if (adminRadioButton.isSelected())
                {
                    urole = adminRadioButton.getText();
                } else
                {
                    error += "Selected role is somehow invalid. Please check again.\n";
                }
                if (!error.matches(""))
                {
                    JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
                } else
                {
                    try ( PreparedStatement psAdd = con.prepareStatement(
                            "INSERT INTO users "
                            + "(Email, Password, UserRole) "
                            + "VALUES (?, ?, ?)"))
                    {
                        psAdd.setString(1, uname);
                        psAdd.setString(2, pword);
                        psAdd.setString(3, urole);
                        psAdd.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(null, "Record adding successful.");
                    refreshData(tableModel);
                    dispose();
                }
            }
        }
    }*/
}
