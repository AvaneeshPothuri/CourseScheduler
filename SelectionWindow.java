import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class SelectionWindow {
    private JFrame frame;
    private List<Course> courseList;
    private List<Instructor> instructorList;
    private List<Classroom> classroomList;
    private List<String> timeSlots = List.of("9AM-10AM", "10AM-11AM", "11AM-12PM");

    public SelectionWindow(List<Course> courses, List<Instructor> instructors, List<Classroom> classrooms) {
        this.courseList = courses;
        this.instructorList = instructors;
        this.classroomList = classrooms;

        frame = new JFrame("Selection Window");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton manualButton = new JButton("Manual");
        JButton automaticButton = new JButton("Automatic");

        automaticButton.addActionListener(e -> generateAndDisplayTimetable());

        frame.add(manualButton);
        frame.add(automaticButton);
        frame.setVisible(true);
    }

    private void generateAndDisplayTimetable() {
        List<TimetableEntry> timetable = new ArrayList<>();
        Map<Instructor, List<String>> instructorAvailability = new HashMap<>();
        Map<Classroom, List<String>> classroomAvailability = new HashMap<>();

        for (Instructor instructor : instructorList) {
            instructorAvailability.put(instructor, new ArrayList<>(timeSlots));
        }
        for (Classroom classroom : classroomList) {
            classroomAvailability.put(classroom, new ArrayList<>(timeSlots));
        }

        for (Course course : courseList) {
            for (String slot : timeSlots) {
                Instructor assignedInstructor = findAvailableInstructor(instructorAvailability, slot);
                Classroom assignedClassroom = findAvailableClassroom(classroomAvailability, slot);

                if (assignedInstructor != null && assignedClassroom != null) {
                    TimetableEntry entry = new TimetableEntry(course, assignedInstructor, assignedClassroom, slot);
                    timetable.add(entry);

                    instructorAvailability.get(assignedInstructor).remove(slot);
                    classroomAvailability.get(assignedClassroom).remove(slot);

                    course.setAssigned(1);
                    break; 
                }
            }
        }

        displayTimetable(timetable);
    }

    private Instructor findAvailableInstructor(Map<Instructor, List<String>> availability, String slot) {
        for (Instructor instructor : availability.keySet()) {
            if (availability.get(instructor).contains(slot)) {
                return instructor;
            }
        }
        return null;
    }

    private Classroom findAvailableClassroom(Map<Classroom, List<String>> availability, String slot) {
        for (Classroom classroom : availability.keySet()) {
            if (availability.get(classroom).contains(slot)) {
                return classroom;
            }
        }
        return null;
    }

    private void displayTimetable(List<TimetableEntry> timetable) {
        JFrame timetableFrame = new JFrame("Generated Timetable");
        timetableFrame.setSize(600, 400);
        timetableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        timetableFrame.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Code", "Course", "Instructor", "Room", "Timeslot"}, 0);
        JTable table = new JTable(tableModel);

        for (TimetableEntry entry : timetable) {
            tableModel.addRow(new Object[]{
                entry.getCourse().getCode(),
                entry.getCourse().getTitle(),
                entry.getInstructor().getName(),
                entry.getClassroom().getRoomNumber(),
                entry.getTimeslot()
            });
        }

        timetableFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        timetableFrame.setVisible(true);
    }
}
