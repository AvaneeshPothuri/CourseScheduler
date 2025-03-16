import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Course {
    private String title;
    private String code;
    private String department;
    private int numStudents;
    private List<Integer> classHours;
    private List<Integer> tutorialHours;
    private Integer labHour;
    private int assigned;

    public Course(String title, String code, String department, int numStudents, 
                  List<Integer> classHours, List<Integer> tutorialHours, Integer labHour) {
        this.title = title;
        this.code = code;
        this.department = department;
        this.numStudents = numStudents;
        this.classHours = classHours;
        this.tutorialHours = tutorialHours;
        this.labHour = labHour;
    }

    public String getTitle() { return title; }
    public String getCode() { return code; }
    public String getDepartment() { return department; }
    public int getNumStudents() { return numStudents; }
    public List<Integer> getClassHours() { return classHours; }
    public List<Integer> getTutorialHours() { return tutorialHours; }
    public Integer getLabHour() { return labHour; }

    public String getName() { return title; }
    public int getLectureCount() { return classHours.size(); }
    public int getTutorialCount() { return tutorialHours.size(); }
    public boolean hasLab() { return labHour != null; }

    public void setAssigned(int assigned) { this.assigned = assigned; }
    public int getAssigned() { return assigned; }

    public String toCSV() {
        return String.join(",",
                title, code, department, 
                String.valueOf(numStudents),
                classHours.stream().map(String::valueOf).collect(Collectors.joining(";")),
                tutorialHours.stream().map(String::valueOf).collect(Collectors.joining(";")),
                labHour != null ? labHour.toString() : ""); // Handle empty lab hour case
    }

    public static Course fromCSV(String csv) {
        String[] parts = csv.split(",");

        if (parts.length < 7) {
            System.err.println("Skipping invalid CSV row: " + csv);
            return null;
        }

        try {
            if (parts[3].equalsIgnoreCase("Students")) {
                return null;
            }

            String title = parts[0].trim();
            String code = parts[1].trim();
            String department = parts[2].trim();
            int numStudents = Integer.parseInt(parts[3].trim());

            List<Integer> classHours = parts[4].trim().isEmpty() ? 
                List.of() : Arrays.stream(parts[4].split(";"))
                                  .map(String::trim)
                                  .map(Integer::parseInt)
                                  .collect(Collectors.toList());

            List<Integer> tutorialHours = parts[5].trim().isEmpty() ? 
                List.of() : Arrays.stream(parts[5].split(";"))
                                  .map(String::trim)
                                  .map(Integer::parseInt)
                                  .collect(Collectors.toList());

            Integer labHour = parts[6].trim().isEmpty() ? null : Integer.parseInt(parts[6].trim());

            return new Course(title, code, department, numStudents, classHours, tutorialHours, labHour);
        } catch (NumberFormatException e) {
            System.err.println("Skipping invalid CSV row (Number Format Error): " + csv);
            return null;
        }
    }

    @Override
    public String toString() {
        return title + " (" + code + ")";
    }
}