public class TimetableEntry {
    private Course course;
    private Instructor instructor;
    private Classroom classroom;
    private String timeslot;

    public TimetableEntry(Course course, Instructor instructor, Classroom classroom, String timeslot) {
        this.course = course;
        this.instructor = instructor;
        this.classroom = classroom;
        this.timeslot = timeslot;
    }

    public Course getCourse() { return course; }
    public Instructor getInstructor() { return instructor; }
    public Classroom getClassroom() { return classroom; }
    public String getTimeslot() { return timeslot; }

    @Override
    public String toString() {
        return course.getCode() + " - " + course.getTitle() + " | " + 
               instructor.getName() + " | " + classroom.getRoomNumber() + 
               " (" + classroom.getType() + ") | " + timeslot;
    }
}
