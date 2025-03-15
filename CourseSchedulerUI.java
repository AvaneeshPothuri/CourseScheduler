import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseSchedulerUI extends JFrame {
    private DefaultListModel<Course> courseListModel;
    private JList<Course> courseList;
    private JTextArea courseDetails;
    private JButton addButton, removeButton, finishButton;
    private Course lastSelectedCourse = null;
    private static final String COURSE_FILE = "courses.csv";

    public CourseSchedulerUI(List<Course> courses) {
        setTitle("Course Scheduler");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(courseList);

        courseDetails = new JTextArea();
        courseDetails.setEditable(false);

        addButton = new JButton("Add Course");
        removeButton = new JButton("Remove Course");
        finishButton = new JButton("Finish");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(finishButton);

        loadCourses();
        for (Course c : courses) {
            courseListModel.addElement(c);
        }

        add(scrollPane, BorderLayout.WEST);
        add(courseDetails, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        courseList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayCourseDetails();
            }
        });

        addButton.addActionListener(e -> addCourse());
        removeButton.addActionListener(e -> removeCourse());
        finishButton.addActionListener(e -> openInstructorUI());

        setVisible(true);
    }

    private void displayCourseDetails() {
        Course selected = courseList.getSelectedValue();
        if (selected != null) {
            if (selected.equals(lastSelectedCourse)) {
                courseDetails.setText("");
                lastSelectedCourse = null;
            } else {
                courseDetails.setText(getCourseDetailsText(selected));
                lastSelectedCourse = selected;
            }
        }
    }

    private void addCourse() {
        CourseForm form = new CourseForm(this);
        form.setVisible(true);
        Course newCourse = form.getCourse();

        if (newCourse != null) {
            courseListModel.addElement(newCourse);
            saveCourses();
        }
    }

    private void removeCourse() {
        Course selected = courseList.getSelectedValue();
        if (selected != null) {
            courseListModel.removeElement(selected);
            courseDetails.setText("");
            saveCourses();
        }
    }

    private void openInstructorUI() {
        new InstructorUI();
        this.dispose();
    }

    private String getCourseDetailsText(Course course) {
        return "Title: " + course.getTitle() +
               "\nCode: " + course.getCode() +
               "\nDepartment: " + course.getDepartment() +
               "\nStudents: " + course.getNumStudents() +
               "\nClass Hours: " + String.join(", ", course.getClassHours()) +
               "\nTutorial Hours: " + String.join(", ", course.getTutorialHours()) +
               "\nLab Hours: " + String.join(", ", course.getLabHours());
    }

    private void saveCourses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COURSE_FILE))) {
            for (int i = 0; i < courseListModel.size(); i++) {
                bw.write(courseListModel.get(i).toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCourses() {
        File file = new File(COURSE_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Course course = Course.fromCSV(line);
                    courseListModel.addElement(course);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CourseSchedulerUI(new ArrayList<>()));
    }
}