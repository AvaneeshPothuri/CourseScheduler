import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Course> courses = Arrays.asList(
            new Course("Data Structures", "CSF213", "Computer Science", 50, Arrays.asList(2), Arrays.asList(4), 5)
        );

        new CourseSchedulerUI(courses);
    }
}
