public class TimetableEntry {
    private String course;
    private String type;
    private String instructor;
    private String room;
    private int timeSlot;

    public TimetableEntry(String course, String type, String instructor, String room, int timeSlot) {
        this.course = course;
        this.type = type;
        this.instructor = instructor;
        this.room = room;
        this.timeSlot = timeSlot;
    }

    public String getCourse() { return course; }
    public String getType() { return type; }
    public String getInstructor() { return instructor; }
    public String getRoom() { return room; }
    public int getTimeSlot() { return timeSlot; }
}
