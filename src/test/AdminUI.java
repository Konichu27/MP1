package test;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class AdminUI extends JDialog
{
    private Connection con;
    private DefaultTableModel tableModel;
    private JTable recordsTable;
    private JButton addRecordButton;
    private JButton updateRecordButton;
    private JButton deleteRecordButton;
    private JButton logoutButton;
    public AdminUI(Connection con) throws SQLException
    {
        this.con = con;
        setTitle("Admin UI");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Email");
        tableModel.addColumn("Password");
        tableModel.addColumn("User Role");
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
        String query = "SELECT * FROM USERS ORDER BY Email";
        PreparedStatement ps = con.prepareStatement(query);
        try (ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                String[] sampleData1 =
                {
                    rs.getString("Email").trim(), rs.getString("Password").trim(), rs.getString("UserRole").trim()
                };
                newTableModel.addRow(sampleData1);
            }
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
            setModal(true);
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
}
