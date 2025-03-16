import java.util.List;

public class TimetableDisplay {
    public static void displayTimetable(List<TimetableEntry> timetable) {
        System.out.println("Generated Timetable:");
        System.out.println("----------------------------------------------------------");
        System.out.printf("%-10s | %-30s | %-15s | %-10s | %-10s%n", 
                          "Code", "Course", "Instructor", "Room", "Timeslot");
        System.out.println("----------------------------------------------------------");
        
        for (TimetableEntry entry : timetable) {
            System.out.printf("%-10s | %-30s | %-15s | %-10s | %-10s%n",
                              entry.getCourse().getCode(),
                              entry.getCourse().getTitle(),
                              entry.getInstructor().getName(),
                              entry.getClassroom().getRoomNumber(),
                              entry.getTimeslot());
        }
        
        System.out.println("----------------------------------------------------------");
    }
}
