import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CourseForm extends JDialog {
    private JTextField titleField, codeField, departmentField, numStudentsField;
    private JTextField classHoursField, tutorialHoursField, labHourField;
    private Course course;

    public CourseForm(JFrame parent) {
        super(parent, "Add Course", true);
        setLayout(new GridLayout(8, 2, 5, 5));

        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Code:"));
        codeField = new JTextField();
        add(codeField);

        add(new JLabel("Department:"));
        departmentField = new JTextField();
        add(departmentField);

        add(new JLabel("No. of Students:"));
        numStudentsField = new JTextField();
        add(numStudentsField);

        add(new JLabel("Class Hours (comma-separated integers):"));
        classHoursField = new JTextField();
        add(classHoursField);

        add(new JLabel("Tutorial Hours (optional, comma-separated integers):"));
        tutorialHoursField = new JTextField();
        add(tutorialHoursField);

        add(new JLabel("Lab Hour (optional, single integer):"));
        labHourField = new JTextField();
        add(labHourField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::saveCourse);
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private void saveCourse(ActionEvent e) {
        try {
            String title = titleField.getText().trim();
            String code = codeField.getText().trim();
            String department = departmentField.getText().trim();
            int numStudents = Integer.parseInt(numStudentsField.getText().trim());

            List<Integer> classHours = classHoursField.getText().trim().isEmpty() ?
                new ArrayList<>() : Arrays.stream(classHoursField.getText().split(","))
                                          .map(String::trim)
                                          .map(Integer::parseInt)
                                          .collect(Collectors.toList());

            List<Integer> tutorialHours = tutorialHoursField.getText().trim().isEmpty() ?
                new ArrayList<>() : Arrays.stream(tutorialHoursField.getText().split(","))
                                          .map(String::trim)
                                          .map(Integer::parseInt)
                                          .collect(Collectors.toList());

            Integer labHour = labHourField.getText().trim().isEmpty() ? null : Integer.parseInt(labHourField.getText().trim());

            course = new Course(title, code, department, numStudents, classHours, tutorialHours, labHour);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter numbers correctly.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Course getCourse() {
        return course;
    }
}
