import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClassroomManager {
    private static final String FILE_PATH = "classrooms.csv";
    private List<Classroom> classrooms = new ArrayList<>();

    public ClassroomManager() {
        loadClassrooms();
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
        saveClassrooms();
    }

    public void removeClassroom(int index) {
        if (index >= 0 && index < classrooms.size()) {
            classrooms.remove(index);
            saveClassrooms();
        }
    }

    private void saveClassrooms() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Classroom classroom : classrooms) {
                writer.write(classroom.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    private void loadClassrooms() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    classrooms.add(new Classroom(parts[0], parts[1], Integer.parseInt(parts[2])));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading data.");
        }
    }
}
