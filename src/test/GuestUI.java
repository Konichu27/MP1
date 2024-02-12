package test;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.JTableHeader;

public class GuestUI extends JDialog
{
    private DefaultTableModel tableModel;
    private JTable recordsTable;
    private JButton logoutButton;
    private ResultSet rs;
    
    public GuestUI(ResultSet rs) throws SQLException
    {
        this.rs = rs;
        setTitle("Guest UI");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        tableModel = new DefaultTableModel();
        
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
        logoutButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // exits the program when logout button is clicked
                dispose();
            }
        });
        recordsTable.setRowHeight(30);
        TableColumnModel columnModel = recordsTable.getColumnModel();
        for (int col = 0; col < columnModel.getColumnCount(); col++)
        {
            columnModel.getColumn(col).setPreferredWidth(150);
        }
        refreshData(tableModel);
        setVisible(true);
    }
    
    // placeholder only
    private void refreshData(DefaultTableModel newTableModel) throws SQLException
    {
        newTableModel.setRowCount(0);
        newTableModel.addColumn("Email");
        newTableModel.addColumn("Password");
        newTableModel.addColumn("User Role");
        while (rs.next()) {
                String[] sampleData1 = {rs.getString("Email").trim(), rs.getString("Password").trim(), rs.getString("UserRole").trim()};
                newTableModel.addRow(sampleData1);
        }
    }
}
