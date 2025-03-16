import java.util.*;

public class TimetableGenerator {
    private List<Course> courses;
    private List<Instructor> instructors;
    private List<Classroom> classrooms;
    private Map<String, Set<Integer>> instructorSchedule = new HashMap<>();
    private Map<String, Set<Integer>> roomSchedule = new HashMap<>();
    private Random random = new Random();

    public TimetableGenerator(List<Course> courses, List<Instructor> instructors, List<Classroom> classrooms) {
        this.courses = courses;
        this.instructors = instructors;
        this.classrooms = classrooms;
    }

    public List<TimetableEntry> createSchedule() {
        List<TimetableEntry> timetable = new ArrayList<>();

        for (Course course : courses) {
            allocateSessions(timetable, course, "Lecture", course.getLectureCount(), "classroom");
            allocateSessions(timetable, course, "Tutorial", course.getTutorialCount(), "classroom");
            allocateSessions(timetable, course, "Lab", course.getLabCount(), "lab");
        }
        return timetable;
    }

    private void allocateSessions(List<TimetableEntry> timetable, Course course, String type, int count, String roomType) {
        for (int i = 0; i < count; i++) {
            Instructor instructor = getAvailableInstructor();
            Classroom room = getAvailableRoom(roomType);
            int timeSlot = getAvailableTimeSlot(instructor.getName(), room.getRoomNumber());

            if (timeSlot != -1) {
                timetable.add(new TimetableEntry(course.getName(), type, instructor.getName(), room.getRoomNumber(), timeSlot));
            }
        }
    }

    private Instructor getAvailableInstructor() {
        return instructors.isEmpty() ? null : instructors.get(random.nextInt(instructors.size()));
    }

    private Classroom getAvailableRoom(String type) {
        return classrooms.stream().filter(r -> r.getType().equals(type)).findFirst().orElse(null);
    }

    private int getAvailableTimeSlot(String instructor, String room) {
        for (int slot = 1; slot <= 8; slot++) {
            instructorSchedule.putIfAbsent(instructor, new HashSet<>());
            roomSchedule.putIfAbsent(room, new HashSet<>());
            if (!instructorSchedule.get(instructor).contains(slot) && !roomSchedule.get(room).contains(slot)) {
                instructorSchedule.get(instructor).add(slot);
                roomSchedule.get(room).add(slot);
                return slot;
            }
        }
        return -1;
    }
}
