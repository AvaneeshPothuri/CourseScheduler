import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class InstructorUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable instructorTable;
    private JTextField nameField, departmentField;
    private static final String FILE_NAME = "instructors.csv";

    public InstructorUI() {
        setTitle("Instructor Management");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Name", "Department"};
        tableModel = new DefaultTableModel(columns, 0);
        instructorTable = new JTable(tableModel);
        loadInstructors();

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Department:"));
        departmentField = new JTextField();
        formPanel.add(departmentField);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Instructor");
        addButton.addActionListener(e -> addInstructor());
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeInstructor());
        JButton finishButton = new JButton("Finish");
        finishButton.addActionListener(e -> finishAndOpenClassroomManager());

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(finishButton);

        add(new JScrollPane(instructorTable), BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addInstructor() {
        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();

        if (name.isEmpty() || department.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{name, department});
        saveInstructors();

        nameField.setText("");
        departmentField.setText("");
    }

    private void removeInstructor() {
        int selectedRow = instructorTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            saveInstructors();
        } else {
            JOptionPane.showMessageDialog(this, "Select an instructor to remove!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void saveInstructors() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                writer.write(tableModel.getValueAt(i, 0) + "," + tableModel.getValueAt(i, 1));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving instructors", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadInstructors() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    tableModel.addRow(new Object[]{parts[0].trim(), parts[1].trim()});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading instructors", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void finishAndOpenClassroomManager() {
        saveInstructors();
        new ClassroomManager();
        dispose();
    }

    public static void main(String[] args) {
        new InstructorUI();
    }
}
