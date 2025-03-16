import javax.swing.*;  
import java.io.*;  
import java.nio.file.*;  
import java.util.ArrayList;  
import java.util.List;

class InstructorManager {

    private static final String FILE_NAME = "instructors.csv";

    public static List<Instructor> loadInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        Path path = Paths.get(FILE_NAME);

        if (!Files.exists(path)) return instructors;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    instructors.add(new Instructor(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading instructors: " + e.getMessage());
        }
        return instructors;
    }

    public static void saveInstructors(List<Instructor> instructors) {
        Path path = Paths.get(FILE_NAME);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Instructor instructor : instructors) {
                writer.write(instructor.getName() + "," + instructor.getDepartment());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving instructors: " + e.getMessage());
        }
    }
}
