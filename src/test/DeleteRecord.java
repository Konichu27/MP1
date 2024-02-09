import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteRecord extends JFrame {

    Container c;
    JLabel userLabel;
    JTextField userField;
    JButton deleteRecordButton, cancel;

    // Assuming the admin's username is "admin" for demonstration purposes
    private static final String ADMIN_USERNAME = "admin";

    public DeleteRecord() {
        setTitle("Delete Record");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        c = getContentPane();
        c.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(10, 10, 465, 400);

        userLabel = new JLabel("Username");
        userLabel.setBounds(80, 50, 150, 30);
        panel.add(userLabel);

        userField = new JTextField();
        userField.setBounds(150, 50, 200, 30);
        panel.add(userField);

        deleteRecordButton = new JButton("Delete Record");
        deleteRecordButton.setBounds(100, 120, 120, 30);
        deleteRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                // Check if the record exists
                if (recordExists(username)) {
                    // Check if trying to delete yourself
                    if (!username.equalsIgnoreCase(ADMIN_USERNAME)) {
                        // Delete the record
                        // Add your logic here to delete the record
                        // For now, we'll just show a message
                        JOptionPane.showMessageDialog(DeleteRecord.this, "Record deleted successfully");
                    } else {
                        JOptionPane.showMessageDialog(DeleteRecord.this, "Error: Cannot delete yourself");
                    }
                } else {
                    // Display a message if the record does not exist
                    JOptionPane.showMessageDialog(DeleteRecord.this, "Error: Record not found");
                }
            }
        });
        panel.add(deleteRecordButton);

        cancel = new JButton("Cancel");
        cancel.setBounds(230, 120, 90, 30);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for cancel button if needed
            }
        });
        panel.add(cancel);

        c.add(panel);

        setVisible(true);
    }

    // Sample method to simulate record existence check
    private boolean recordExists(String username) {
        // Add your logic here to check if the record exists
        // For now, let's assume it always exists (except for admin)
        return !username.equalsIgnoreCase(ADMIN_USERNAME);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteRecord deleteRecord = new DeleteRecord();
        });
    }
}


// create a joption pane
//j dialogue if the record cannot be found 
//j dialogue if the record your are trying to delete is yourself 