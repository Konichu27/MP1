import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTable recordsTable;
    private JButton addRecordButton;
    private JButton updateRecordButton;
    private JButton deleteRecordButton;
    private JButton logoutButton;

    public AdminUI() {
        setTitle("Admin UI");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        recordsTable.setRowHeight(30);

       
        TableColumnModel columnModel = recordsTable.getColumnModel();
        for (int col = 0; col < columnModel.getColumnCount(); col++) {
            columnModel.getColumn(col).setPreferredWidth(150);
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminUI adminUI = new AdminUI();
            adminUI.addSampleData();
        });
    }

    // placeholder only
    private void addSampleData() {
        String[] sampleData1 = {"admin1@example.com", "admin1", "Admin"};
        String[] sampleData2 = {"guest1@example.com", "guest1", "Guest"};
        tableModel.addRow(sampleData1);
        tableModel.addRow(sampleData2);
    }
}
