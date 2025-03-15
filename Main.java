import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Course> courses = Arrays.asList(
            new Course("Data Structures", "CSF213", "CS", 50, 
                       Arrays.asList("M 2", "W 3"), Arrays.asList("F 4"), Arrays.asList("Th 5")),
            new Course("Digital Circuits", "ECF201", "ECE", 40, 
                       Arrays.asList("T 1", "Th 3"), Arrays.asList("W 2"), Arrays.asList("F 5"))
        );
        
        new CourseSchedulerUI(courses);
    }
}
