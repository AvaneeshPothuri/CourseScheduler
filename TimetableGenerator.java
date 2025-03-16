import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimetableGenerator {
    private List<Course> courses;
    private List<Instructor> instructors;
    private List<Classroom> classrooms;
    private List<String> availableTimeslots;

    public TimetableGenerator(List<Course> courses, List<Instructor> instructors, 
                              List<Classroom> classrooms, List<String> availableTimeslots) {
        this.courses = courses;
        this.instructors = instructors;
        this.classrooms = classrooms;
        this.availableTimeslots = availableTimeslots;
    }

    public List<TimetableEntry> generateTimetable() {
        List<TimetableEntry> timetable = new ArrayList<>();
        Random random = new Random();

        for (Course course : courses) {
            if (instructors.isEmpty() || classrooms.isEmpty() || availableTimeslots.isEmpty()) {
                System.out.println("Not enough resources to schedule all courses.");
                break;
            }

            Instructor instructor = instructors.get(random.nextInt(instructors.size()));
            Classroom classroom = classrooms.get(random.nextInt(classrooms.size()));
            String timeslot = availableTimeslots.get(random.nextInt(availableTimeslots.size()));

            TimetableEntry entry = new TimetableEntry(course, instructor, classroom, timeslot);
            timetable.add(entry);
        }

        return timetable;
    }
}
