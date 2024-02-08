import javax.swing.*;
import java.awt.*;

public class AddRecord extends JFrame {

    Container c;
    JLabel userLabel, passLabel, confPLabel, roleLabel;
    JTextField userField;
    JPasswordField passField, confPass;
    JButton add, cancel;
    JRadioButton adminRadioButton, guestRadioButton;
    ButtonGroup roleGroup;

    public AddRecord() {
        setTitle("Add Record");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        c.add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddRecord addRecord = new AddRecord();
        });
    }
}
