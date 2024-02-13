package test;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.JTableHeader;

class GuestUI extends JDialog
{
    private Connection con;
    private DefaultTableModel tableModel;
    private JTable recordsTable;
    private JButton logoutButton;
    public GuestUI(Connection con) throws SQLException
    {
        this.con = con;
        setTitle("Guest UI");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Email");
        tableModel.addColumn("Password");
        tableModel.addColumn("User Role");
        recordsTable = new JTable(tableModel);
        // layout table
        recordsTable.setIntercellSpacing(new Dimension(15, 5));
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        recordsTable.setRowSorter(sorter);
        JTableHeader header = recordsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) recordsTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        add(scrollPane, BorderLayout.CENTER);
        // logout button
        logoutButton = new JButton("Logout");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);
        // LISTENERS
        logoutButton.addActionListener((ActionEvent e) ->
        {
            dispose(); // exits the program when logout button is clicked
        });
        recordsTable.setRowHeight(30);
        TableColumnModel columnModel = recordsTable.getColumnModel();
        for (int col = 0; col < columnModel.getColumnCount(); col++)
        {
            columnModel.getColumn(col).setPreferredWidth(150);
        }
        refreshData(tableModel);
        recordsTable.setDefaultEditor(Object.class, null);
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
}

/*class AdminUI extends JDialog implements ActionListener
{
    private ResultSet rs;
    private DefaultTableModel tableModel;
    private JTable recordsTable;
    private JButton addRecordButton;
    private JButton updateRecordButton;
    private JButton deleteRecordButton;
    private JButton logoutButton;
    public AdminUI(ResultSet rs) throws SQLException
    {
        setTitle("Admin UI");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
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
        recordsTable.setDefaultEditor(Object.class, null);
        setVisible(true);
    }
    // placeholder only
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
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == addRecordButton) {
            
        } else
        if (e.getSource() == updateRecordButton) {
            
        } else
        if (e.getSource() == deleteRecordButton) {
            
        }
    }
}*/