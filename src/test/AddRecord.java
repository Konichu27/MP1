import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddRecord extends JDialog {
    private Container c;
    private Connection con;
    private JLabel userLabel, passLabel, confPLabel, roleLabel;
    private JTextField userField;
    private JPasswordField passField, confPass;
    private JButton add, cancel;
    private JRadioButton adminRadioButton, guestRadioButton;
    private ButtonGroup roleGroup;

    public AddRecord(Connection con) {
        this.con = con;
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
    
    private void addRecord() throws SQLException {
        String error = "";
        String uname = userField.getText();
        String pword = passField.getText();
        String confPword = confPass.getText();
        String urole = "";
        
        if (uname.equals("") || uname == null) {
            error += "Username must not be empty.\n";
        }
        if (pword.equals("") || pword == null) {
            error += "Password must not be empty.\n";
        } else {
            if (!pword.matches(".*[A-Z]+.*"))
                error += "Password must have at least 1 uppercase letter.\n";
            if (!pword.matches(".*[a-z]+.*"))
                error += "Password must have at least 1 lowercase letter.\n";
            if (!pword.matches(".*[0-9]+.*"))
                error += "Password must have at least 1 number.\n";
            if (!pword.matches(".*[^A-Za-z0-9]+.*"))
                error += "Password must have at least 1 special character.\n";
        }
        if (!pword.matches(confPword)) {
            error += "Passwords do not match.\n";
        }
        if (guestRadioButton.isSelected()) {
            urole = guestRadioButton.getText();
        } else
        if (adminRadioButton.isSelected()) {
            urole = adminRadioButton.getText();
        }
        else {
            error += "Selected role is somehow invalid. Please check again.\n";
        }
        
        if (!error.matches("")) {
            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try (PreparedStatement psAdd = con.prepareStatement(
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
            dispose();
        }
    }
    
}
