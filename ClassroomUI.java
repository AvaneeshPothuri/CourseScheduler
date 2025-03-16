import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClassroomUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField roomNumberField, typeField, capacityField;
    private ClassroomManager classroomManager;

    public ClassroomUI() {
        classroomManager = new ClassroomManager();

        frame = new JFrame("Classroom Manager");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Room Number", "Type", "Capacity"}, 0);
        table = new JTable(tableModel);
        loadTableData();

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
            Classroom classroom = new Classroom(roomNumber, type, capacity);
            classroomManager.addClassroom(classroom);
            tableModel.addRow(new Object[]{roomNumber, type, capacity});

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClassroomUI::new);
    }
}
