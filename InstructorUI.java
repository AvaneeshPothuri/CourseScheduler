import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*; 
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;


public class InstructorUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable instructorTable;
    private JTextField nameField, departmentField;
    private List<Instructor> instructors;

    public InstructorUI() {
        setTitle("Instructor Management");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Name", "Department"};
        tableModel = new DefaultTableModel(columns, 0);
        instructorTable = new JTable(tableModel);

        instructors = InstructorManager.loadInstructors();
        loadInstructorsIntoTable();

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

    private void loadInstructorsIntoTable() {
        for (Instructor instructor : instructors) {
            tableModel.addRow(new Object[]{instructor.getName(), instructor.getDepartment()});
        }
    }

    private void addInstructor() {
        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();

        if (name.isEmpty() || department.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Instructor instructor = new Instructor(name, department);
        instructors.add(instructor);
        tableModel.addRow(new Object[]{name, department});
        InstructorManager.saveInstructors(instructors);

        nameField.setText("");
        departmentField.setText("");
    }

    private void removeInstructor() {
        int selectedRow = instructorTable.getSelectedRow();
        if (selectedRow != -1) {
            instructors.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            InstructorManager.saveInstructors(instructors);
        } else {
            JOptionPane.showMessageDialog(this, "Select an instructor to remove!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void finishAndOpenClassroomManager() {
        InstructorManager.saveInstructors(instructors);
    
        SwingUtilities.invokeLater(() -> {
            new ClassroomUI();
        });
    
        dispose();
    }
    

    public static void main(String[] args) {
        new InstructorUI();
    }
}
