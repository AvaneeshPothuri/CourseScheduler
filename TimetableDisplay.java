import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TimetableDisplay extends JFrame {
    public TimetableDisplay(List<TimetableEntry> timetable) {
        setTitle("Generated Timetable");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Course", "Type", "Instructor", "Room", "Time Slot"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (TimetableEntry entry : timetable) {
            model.addRow(new Object[]{entry.getCourse(), entry.getType(), entry.getInstructor(), entry.getRoom(), entry.getTimeSlot()});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
        setVisible(true);
    }
}
