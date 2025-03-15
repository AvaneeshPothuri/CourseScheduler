import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorUI extends JFrame {
    private DefaultListModel<String> instructorListModel;
    private JList<String> instructorList;
    private JTextField nameField, departmentField;
    private static final String FILE_NAME = "instructors.csv";

    public InstructorUI() {
        setTitle("Instructor Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        instructorListModel = new DefaultListModel<>();
        instructorList = new JList<>(instructorListModel);
        loadInstructors();
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Department:"));
        departmentField = new JTextField();
        formPanel.add(departmentField);
        
        JButton addButton = new JButton("Add Instructor");
        addButton.addActionListener(e -> addInstructor());
        formPanel.add(addButton);
        
        JButton removeButton = new JButton("Remove Instructor");
        removeButton.addActionListener(e -> removeInstructor());
        formPanel.add(removeButton);
        
        add(new JScrollPane(instructorList), BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private void addInstructor() {
        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();
        
        if (name.isEmpty() || department.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String instructor = name + ", " + department;
        instructorListModel.addElement(instructor);
        saveInstructors();
        
        nameField.setText("");
        departmentField.setText("");
    }
    
    private void removeInstructor() {
        int selectedIndex = instructorList.getSelectedIndex();
        if (selectedIndex != -1) {
            instructorListModel.remove(selectedIndex);
            saveInstructors();
        }
    }
    
    private void saveInstructors() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < instructorListModel.size(); i++) {
                writer.write(instructorListModel.get(i));
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
                instructorListModel.addElement(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading instructors", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        new InstructorUI();
    }
}
