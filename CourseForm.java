import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseForm extends JDialog {
    private JTextField titleField, codeField, departmentField, numStudentsField;
    private JTextField classHoursField, tutorialHoursField, labHoursField;
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

        add(new JLabel("Class Hours (comma-separated):"));
        classHoursField = new JTextField();
        add(classHoursField);

        add(new JLabel("Tutorial Hours (optional):"));
        tutorialHoursField = new JTextField();
        add(tutorialHoursField);

        add(new JLabel("Lab Hours (optional):"));
        labHoursField = new JTextField();
        add(labHoursField);

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
            String title = titleField.getText();
            String code = codeField.getText();
            String department = departmentField.getText();
            int numStudents = Integer.parseInt(numStudentsField.getText());
            List<String> classHours = Arrays.asList(classHoursField.getText().split(","));
            List<String> tutorialHours = tutorialHoursField.getText().isEmpty()
                    ? new ArrayList<>() : Arrays.asList(tutorialHoursField.getText().split(","));
            List<String> labHours = labHoursField.getText().isEmpty()
                    ? new ArrayList<>() : Arrays.asList(labHoursField.getText().split(","));

            course = new Course(title, code, department, numStudents, classHours, tutorialHours, labHours);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Course getCourse() {
        return course;
    }
}
