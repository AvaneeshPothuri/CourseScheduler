import java.util.*;

public class TimetableGenerator {
    private List<Course> courses;
    private List<Instructor> instructors;
    private List<Classroom> classrooms;
    private List<String> timeslots = Arrays.asList("9AM-10AM", "10AM-11AM", "11AM-12PM", "1PM-2PM", "2PM-3PM");

    public TimetableGenerator(List<Course> courses, List<Instructor> instructors, List<Classroom> classrooms) {
        this.courses = courses;
        this.instructors = instructors;
        this.classrooms = classrooms;
    }

    public List<TimetableEntry> generateTimetable() {
        List<TimetableEntry> timetable = new ArrayList<>();
        Set<String> occupiedClassrooms = new HashSet<>();
        Set<String> occupiedInstructors = new HashSet<>();

        for (String timeslot : timeslots) {
            Collections.shuffle(courses);
            
            for (Course course : courses) {
                if (occupiedInstructors.size() == instructors.size() || occupiedClassrooms.size() == classrooms.size()) {
                    break; 
                }

                Instructor instructor = null;
                for (Instructor i : instructors) {
                    if (!occupiedInstructors.contains(i.getName()) && i.isAvailableFor(course.getName())) {
                        instructor = i;
                        occupiedInstructors.add(i.getName());
                        break;
                    }
                }

                Classroom classroom = null;
                for (Classroom c : classrooms) {
                    if (!occupiedClassrooms.contains(c.getRoomNumber()) && c.getCapacity() >= course.getNumStudents()) {
                        classroom = c;
                        occupiedClassrooms.add(c.getRoomNumber());
                        break;
                    }
                }
                
                if (instructor != null && classroom != null) {
                    timetable.add(new TimetableEntry(course.getTitle(), instructor.getName(), classroom.getRoomNumber(), timeslot));
                }
            }

            occupiedInstructors.clear();
            occupiedClassrooms.clear();
        }
        
        return timetable;
    }
}