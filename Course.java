import java.util.Arrays;
import java.util.List;

public class Course {
    private String title;
    private String code;
    private String department;
    private int numStudents;
    private List<String> classHours;
    private List<String> tutorialHours;
    private List<String> labHours;
    private int assigned;

    public Course(String title, String code, String department, int numStudents, 
                  List<String> classHours, List<String> tutorialHours, List<String> labHours) {
        this.title = title;
        this.code = code;
        this.department = department;
        this.numStudents = numStudents;
        this.classHours = classHours;
        this.tutorialHours = tutorialHours;
        this.labHours = labHours;
    }

    public String getTitle() { return title; }
    public String getCode() { return code; }
    public String getDepartment() { return department; }
    public int getNumStudents() { return numStudents; }
    public List<String> getClassHours() { return classHours; }
    public List<String> getTutorialHours() { return tutorialHours; }
    public List<String> getLabHours() { return labHours; }

    public String getName() { return title; }
    public int getLectureCount() { return classHours.size(); }
    public int getTutorialCount() { return tutorialHours.size(); }
    public int getLabCount() { return labHours.size(); }

    public void setAssigned(int assigned) {this.assigned = assigned;}
    public int getAssigned() {return assigned;}

    public String toCSV() {
        return String.join(",", title, code, department, 
                String.valueOf(numStudents),
                String.join(";", classHours),
                String.join(";", tutorialHours),
                String.join(";", labHours));
    }

    public static Course fromCSV(String csv) {
        String[] parts = csv.split(",");

        if (parts.length < 7) return null;
    
        try {
            if (parts[3].equalsIgnoreCase("Students")) {
                return null;
            }
    
            String title = parts[0];
            String code = parts[1];
            String department = parts[2];
            int numStudents = Integer.parseInt(parts[3]);
    
            List<String> classHours = Arrays.asList(parts[4].split(";"));
            List<String> tutorialHours = Arrays.asList(parts[5].split(";"));
            List<String> labHours = Arrays.asList(parts[6].split(";"));
    
            return new Course(title, code, department, numStudents, classHours, tutorialHours, labHours);
        } catch (NumberFormatException e) {
            System.err.println("Skipping invalid CSV row: " + csv);
            return null;
        }
    }

    @Override
    public String toString() {
        return title + " (" + code + ")";
    }
}
