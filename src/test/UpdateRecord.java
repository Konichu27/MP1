/*
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UpdateRecord extends JDialog {
// public class UpdateRecord extends JDialog { 
    private Connection con;
    private String adminUname;
    
    Container c;
    JLabel userLabel, passLabel, confPLabel, roleLabel;
    JTextField userField;
    JPasswordField passField, confPass;
    JButton add, cancel;
    JRadioButton adminRadioButton, guestRadioButton;
    ButtonGroup roleGroup;

    public UpdateRecord(Connection con, String adminUname) {
        this.con = con;
        this.adminUname = adminUname;
        
        setTitle("Update Record");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setModal(true);

        c = getContentPane();
        c.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(10, 10, 465, 400);

        userLabel = new JLabel("Username");
        passLabel = new JLabel("Password");
        confPLabel = new JLabel("Confirm Password");
        roleLabel = new JLabel("User Role");

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

        add = new JButton("Update Record");
        add.setBounds(120, 270, 120, 30);
        panel.add(add);

        cancel = new JButton("Cancel");
        cancel.setBounds(250, 270, 90, 30);
        panel.add(cancel);

        c.add(panel);

        setVisible(true);
    }
    
    private void updateRecord() throws SQLException
        {
            String error = "";
            String uname = userField.getText();
            String pword = passField.getText();
            String confPword = confPass.getText();
            String urole = "";
            String checkedUrole = "";
            if (uname.equals("") || uname == null)
            {
                error += "Username must not be empty.\n";
            } else {
                try (PreparedStatement psCheck = con.prepareStatement("SELECT UserRole FROM users WHERE Email = ?");) {
                    psCheck.setString(1, uname);
                    try (ResultSet rsCheck = psCheck.executeQuery()) {
                        rsCheck.next();
                        checkedUrole = rsCheck.getString("UserRole").trim();
                        if (checkedUrole.equals("") || checkedUrole == null) {
                            error += "Username does not exist in the database.\n";
                        }
                    }
                }
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
            } else if (checkedUrole.toUpperCase().equals("admin"))
            {
                error += "Selected role is invalid. Please check again.\n";
            }
            if (!error.matches(""))
            {
                JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
            } else
            {
                if (checkedUrole.toUpperCase().equals("ADMIN"))
                {
                    try (PreparedStatement psAdd = con.prepareStatement(
                            "UPDATE users "
                            + "SET Password = ?, UserRole = ? "
                            + "WHERE Email = ?"))
                    {
                        psAdd.setString(1, uname);
                        psAdd.setString(2, pword);
                        psAdd.setString(3, urole);
                        psAdd.executeUpdate();
                    }
                } else {
                    try (PreparedStatement psAdd = con.prepareStatement(
                            "UPDATE users " +
                            "SET Password = ? " + 
                            "WHERE Email = ?"))
                    {
                        psAdd.setString(1, uname);
                        psAdd.setString(2, pword);
                        psAdd.executeUpdate();
                    }
                }
                JOptionPane.showMessageDialog(null, "Record adding successful.");
                refreshData(tableModel);
        }
    }
}
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdateRecord updateRecord = new UpdateRecord();
        });
    }
}*/
