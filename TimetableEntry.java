public class TimetableEntry {
    private String course;
    private String instructor;
    private String classroom;
    private String timeslot;

    public TimetableEntry(String course, String instructor, String classroom, String timeslot) {
        this.course = course;
        this.instructor = instructor;
        this.classroom = classroom;
        this.timeslot = timeslot;
    }

    public String getCourse() {
        return course;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getTimeslot() {
        return timeslot;
    }

    @Override
    public String toString() {
        return timeslot + " | " + course + " | " + instructor + " | " + classroom;
    }
}
