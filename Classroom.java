public class Classroom {
    private String roomNumber;
    private String type;
    private int capacity;

    public Classroom(String roomNumber, String type, int capacity) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return roomNumber + "," + type + "," + capacity;
    }
}
