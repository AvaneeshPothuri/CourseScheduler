import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SelectionWindow extends JFrame {
    private List<Course> courses;
    private List<Instructor> instructors;
    private List<Classroom> classrooms;

    public SelectionWindow(java.util.List<Course> courses, java.util.List<Instructor> instructors, java.util.List<Classroom> classrooms){
        this.courses = courses;
        this.instructors = instructors;
        this.classrooms = classrooms;

        setTitle("Selection Window");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel headingLabel = new JLabel("Select How You Want to Create Your Timetable");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 10));

        JButton manualButton = new JButton("Manual");
        JButton automaticButton = new JButton("Automatic");

        styleButton(manualButton, new Color(70, 130, 180));
        styleButton(automaticButton, new Color(34, 139, 34));

        buttonPanel.add(manualButton);
        buttonPanel.add(automaticButton);

        automaticButton.addActionListener(e -> generateTimetable());

        mainPanel.add(headingLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void generateTimetable() {
        TimetableGenerator generator = new TimetableGenerator(courses, instructors, classrooms);
        List<TimetableEntry> timetable = generator.createSchedule();

        new TimetableDisplay(timetable);
    }
}
