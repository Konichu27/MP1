/**
 * @author Edrine Frances
 * @author Leonne Matthew Dayao
 * @author Rayna Gulifardo
 * 2CSC - CICS - University of Santo Tomas
 * 
 * Swing GUI for database.
 */

/*
package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class LoginFrame extends JFrame implements ActionListener {
    Container c;
    Connection con;
    JLabel userLabel, passLabel;
    JTextField userField;
    JPasswordField passField;
    JButton loginButton;
    private int count = 0;
 
    LoginFrame(Connection con) {
        this.con = con;
        
        setTitle("Login Form");
        setSize(375, 275);
        setLocation(700,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        userLabel = new JLabel("Username");
        passLabel = new JLabel("Password");

        userLabel.setBounds(40,50,100,30);
        passLabel.setBounds(40,100,100,30);

        c.add(userLabel);
        c.add(passLabel);

        userField = new JTextField();
        userField.setBounds(120,50,200,30);
        c.add(userField);

        passField = new JPasswordField();
        passField.setBounds(120,100,200,30);
        c.add(passField);

        loginButton = new JButton("Login");
        loginButton.setBounds(140,150,70,30);
        c.add(loginButton);

        setVisible(true);

        // TODO
        // - 3-counter limit w/ corresp. errors, login error window, connection error window/s(?)
        // - Driver & DB connection code
        // - Guest & admin windows (see MP1 .xsl guidelines)
        // - Some other stuff perhaps being missed
        
        
    }

    public void actionPerformed(ActionEvent e) throws SQLException
    {
        if (e.getSource() == loginButton) {
                // Verify W/ Server
                String VER_QUERY = "SELECT * FROM USERS "
                        + "WHERE Email = ?";
                Account acc = new Account(userField.getText(), passField.getText());
                
                PreparedStatement accPs = con.prepareStatement(VER_QUERY);
                accPs.setString(1, acc.getUname());
                ResultSet accRs = accPs.executeQuery();
                if (accRs.next()) { // next() method returns false if no corresp. entry is found.
                    if (accRs.getString("Password").matches(acc.getPword())) {
                        acc.setUrole(accRs.getString("UserRole"));
                        JOptionPane.showMessageDialog(null, "Successfully logged in.");
                        accRs.close();
                        accPs.close();
                        this.dispose();
                        return;
                    }
                }
                
                // "INCORRECT" Error Window
                if (count < 3) {
                    JOptionPane.showMessageDialog(null,
                            "Incorrect username and/or password." + (3 - count++) + " tries left.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                // "FINISHED" Error Window
                else {
                    JOptionPane.showMessageDialog(null, "Sorry, You have reached the limit of 3 tries. Good bye!", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    accRs.close();
                    accPs.close();
                    con.close();
                    System.exit(0);
            }
        }
    }
}
*/