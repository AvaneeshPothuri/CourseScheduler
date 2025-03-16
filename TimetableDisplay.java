import javax.swing.*;
import java.awt.*;
import java.util.List;

class TimetableDisplay {
    public TimetableDisplay(List<TimetableEntry> timetable) {
        JFrame frame = new JFrame("Generated Timetable");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        String[] columns = {"Course", "Instructor", "Classroom", "TimeSlot"};
        String[][] data = new String[timetable.size()][4];
        
        for (int i = 0; i < timetable.size(); i++) {
            TimetableEntry entry = timetable.get(i);
            data[i] = new String[]{entry.getCourse(), entry.getInstructor(), entry.getClassroom(), entry.getTimeslot()};
        }
        
        JTable table = new JTable(data, columns);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
