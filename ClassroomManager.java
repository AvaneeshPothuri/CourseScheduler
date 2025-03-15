import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ClassroomManager {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField roomNumberField, typeField, capacityField;
    private static final String FILE_PATH = "classrooms.csv";

    public ClassroomManager() {
        frame = new JFrame("Classroom Manager");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Room Number", "Type", "Capacity"}, 0);
        table = new JTable(tableModel);
        loadClassrooms();

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.add(new JLabel("Room Number:"));
        roomNumberField = new JTextField();
        formPanel.add(roomNumberField);
        
        formPanel.add(new JLabel("Type (Classroom/Lab):"));
        typeField = new JTextField();
        formPanel.add(typeField);
        
        formPanel.add(new JLabel("Capacity:"));
        capacityField = new JTextField();
        formPanel.add(capacityField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton finishButton = new JButton("Finish");

        addButton.addActionListener(e -> addClassroom());
        removeButton.addActionListener(e -> removeClassroom());
        finishButton.addActionListener(e -> {
            saveClassrooms();
            frame.dispose();
        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(finishButton);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addClassroom() {
        String roomNumber = roomNumberField.getText().trim();
        String type = typeField.getText().trim();
        String capacityStr = capacityField.getText().trim();

        if (roomNumber.isEmpty() || type.isEmpty() || capacityStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int capacity = Integer.parseInt(capacityStr);
            tableModel.addRow(new Object[]{roomNumber, type, capacity});
            saveClassrooms();
            roomNumberField.setText("");
            typeField.setText("");
            capacityField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Capacity must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeClassroom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            saveClassrooms();
        } else {
            JOptionPane.showMessageDialog(frame, "Select a row to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveClassrooms() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                writer.write(tableModel.getValueAt(i, 0) + "," +
                             tableModel.getValueAt(i, 1) + "," +
                             tableModel.getValueAt(i, 2));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadClassrooms() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    tableModel.addRow(new Object[]{parts[0], parts[1], Integer.parseInt(parts[2])});
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("No previous data found or file is corrupted.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClassroomManager::new);
    }
}