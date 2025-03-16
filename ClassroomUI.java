import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClassroomUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField roomNumberField, typeField, capacityField;
    private ClassroomManager classroomManager;
    private List<Course> courseList;
    private List<Instructor> instructorList;

    public ClassroomUI(List<Course> courses, List<Instructor> instructors) {
        this.courseList = courses;
        this.instructorList = instructors;
        this.classroomManager = new ClassroomManager();

        frame = new JFrame("Classroom Manager");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Room Number", "Type", "Capacity"}, 0);
        table = new JTable(tableModel);
        loadTableData();

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Room Number:"));
        roomNumberField = new JTextField();
        formPanel.add(roomNumberField);

        formPanel.add(new JLabel("Type (Classroom/Lab):"));
        typeField = new JTextField();
        formPanel.add(typeField);

        formPanel.add(new JLabel("Capacity:"));
        capacityField = new JTextField();
        formPanel.add(capacityField);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton finishButton = new JButton("Finish");

        addButton.addActionListener(e -> addClassroom());
        removeButton.addActionListener(e -> removeClassroom());
        finishButton.addActionListener(e -> openSelectionWindow()); 

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(finishButton);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void openSelectionWindow() {
        new SelectionWindow();
        frame.dispose();
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
            if (capacity <= 0) {
                throw new NumberFormatException();
            }
            Classroom classroom = new Classroom(roomNumber, type, capacity);
            classroomManager.addClassroom(classroom);
            tableModel.addRow(new Object[]{roomNumber, type, capacity});
            clearFormFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Capacity must be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeClassroom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            classroomManager.removeClassroom(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(frame, "Select a row to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTableData() {
        for (Classroom classroom : classroomManager.getClassrooms()) {
            tableModel.addRow(new Object[]{classroom.getRoomNumber(), classroom.getType(), classroom.getCapacity()});
        }
    }

    private void clearFormFields() {
        roomNumberField.setText("");
        typeField.setText("");
        capacityField.setText("");
    }
}
