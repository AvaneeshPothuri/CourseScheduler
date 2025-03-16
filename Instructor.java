import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class Instructor {
    private String name;
    private String department;

    public Instructor(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return name + "," + department;
    }

    public boolean isAvailableFor(String courseName) {
        String filePath = "instructors.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String instructorName = parts[0].trim();
                    List<String> availableCourses = Arrays.asList(parts[2].trim().split(";"));
                    if (instructorName.equals(this.name) && availableCourses.contains(courseName)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Instructor getAvailableInstructor(String courseName) {
        String filePath = "instructors.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String instructorName = parts[0].trim();
                    String department = parts[1].trim();
                    List<String> availableCourses = Arrays.asList(parts[2].trim().split(";"));
                    if (availableCourses.contains(courseName)) {
                        return new Instructor(instructorName, department);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
