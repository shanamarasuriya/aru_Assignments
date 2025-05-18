//Student ID- 2370967
package matcher;

import model.CV;
import model.Job;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


// * This class will compare a CV against a job posting and calculates a match percentage based on required skills.

public class JobMatcher {

    public double calculateMatch(CV cv, Job job) {
        if (cv == null || job == null || cv.getSkills() == null || job.getRequiredSkills() == null) {
            return 0.0;
        }


        // Normalize skills to lowercase for case-insensitive comparison
        Set<String> cvSkills = cv.getSkills().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        List<String> jobSkills = job.getRequiredSkills();

        // Count how many required skills match the candidate's skills
        long matched = jobSkills.stream()
                .map(String::toLowerCase)
                .filter(cvSkills::contains)
                .count();

        return (matched * 100.0) / jobSkills.size();
    }
}
