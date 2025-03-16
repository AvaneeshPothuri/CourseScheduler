import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.stream.Collectors;

public class SelectionWindow {
    
    private void generateAndDisplayTimetable() {
        List<Course> courses = loadCoursesFromCSV("courses.csv");
        List<Instructor> instructors = loadInstructorsFromCSV("instructors.csv");
        List<Classroom> classrooms = loadClassroomsFromCSV("classrooms.csv");

        TimetableGenerator timetableGenerator = new TimetableGenerator(courses, instructors, classrooms);
        List<TimetableEntry> timetable = timetableGenerator.generateTimetable();
        
        new TimetableDisplay(timetable);
        saveTimetableToCSV(timetable);
    }

    private List<Course> loadCoursesFromCSV(String filename) {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    String title = data[0].trim();
                    String code = data[1].trim();
                    String department = data[2].trim();
                    int students = Integer.parseInt(data[3].trim());
                    int classHours = Integer.parseInt(data[4].trim());
                    int tutorialHours = Integer.parseInt(data[5].trim());
                    int labHours = data.length > 6 ? Integer.parseInt(data[6].trim()) : 0;

                    courses.add(new Course(title, code, department, students, classHours, tutorialHours, labHours));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private List<Instructor> loadInstructorsFromCSV(String filename) {
        List<Instructor> instructors = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    String name = data[0].trim();
                    String department = data[1].trim();
                    instructors.add(new Instructor(name, department));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instructors;
    }

    private List<Classroom> loadClassroomsFromCSV(String filename) {
        List<Classroom> classrooms = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String roomNumber = data[0].trim();
                    String type = data[1].trim();
                    int capacity = Integer.parseInt(data[2].trim());
                    classrooms.add(new Classroom(roomNumber, type, capacity));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classrooms;
    }
}
