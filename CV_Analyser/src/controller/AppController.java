//Student ID- 2370967
package controller;

import model.*;
import matcher.JobMatcher;
import parser.ResumeParser;

import java.io.IOException;
import java.util.*;

public class AppController {
    private final DataStorage storage = new DataStorage();
    private final ResumeParser parser = new ResumeParser();
    private final JobMatcher matcher = new JobMatcher();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("========================================");
            System.out.println("        Smart CV Analyser - NexiGen     ");
            System.out.println("========================================");
            System.out.println("1) Candidate Mode");
            System.out.println("2) Recruiter Mode");
            System.out.println("3) Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleCandidateMode();
                case "2" -> handleRecruiterMode();
                case "3" -> {
                    System.out.println("Thank you for using Smart CV Analyser. Goodbye!");
                    return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void handleCandidateMode() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        // Validate email format
        if (!isValidEmail(email)) {
            System.out.println("Invalid email address. Please enter a valid email (e.g., name@example.com).");
            return;
        }
        List<Job> jobs = storage.getAllJobs();
        if (jobs.isEmpty()) {
            System.out.println("No job postings available at this time.");
            return;
        }

        System.out.println("\nAvailable Job Postings:");
        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            System.out.printf("%d) %s at %s%n", i + 1, job.getTitle(), job.getCompany());
        }

        System.out.print("Select a job by ID: ");
        int jobId = Integer.parseInt(scanner.nextLine()) - 1;
        if (jobId < 0 || jobId >= jobs.size()) {
            System.out.println("Invalid job ID.");
            return;
        }

        Job selectedJob = jobs.get(jobId);
        System.out.println("\nJob Details:");
        System.out.println("Title       : " + selectedJob.getTitle());
        System.out.println("Company     : " + selectedJob.getCompany());
        System.out.println("Location    : " + selectedJob.getLocation());
        System.out.println("Description : " + selectedJob.getDescription());
        System.out.println(" Skills     : " + String.join(", ", selectedJob.getRequiredSkills()));

        System.out.print("Would you like to apply? (Y/N): ");
        String apply = scanner.nextLine();
        if (!apply.equalsIgnoreCase("y")) return;

        System.out.print("Enter file path (.txt, .docx, .pdf): ");
        String path = scanner.nextLine();

        try {
            CV cv = parser.parse(email, path);
            storage.addCV(cv);
            double score = matcher.calculateMatch(cv, selectedJob);
            System.out.printf("Your match score: %.2f%%%n", score);
        } catch (IOException e) {
            System.out.println("Error reading CV: " + e.getMessage());
        }
    }

    private void handleRecruiterMode() {
        System.out.print("Enter recruiter password: ");
        String password = scanner.nextLine();
        if (!password.equals("123")) {
            System.out.println("Access denied. Incorrect Password");
            return;
        }

        while (true) {
            System.out.println();
            System.out.println("Recruiter Menu:");
            System.out.println("\n1) View Ranked Candidates");
            System.out.println("2) Add Job Posting");
            System.out.println("3) Back");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> viewRankedCandidates();
                case "2" -> addJobPosting();
                case "3" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewRankedCandidates() {
        List<Job> jobs = storage.getAllJobs();
        if (jobs.isEmpty()) {
            System.out.println("No jobs available.");
            return;
        }

        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            System.out.printf("%d) %s at %s%n", i + 1, job.getTitle(), job.getCompany());
        }

        System.out.print("Select a job by ID: ");
        int jobId = Integer.parseInt(scanner.nextLine()) - 1;
        Job job = storage.getJob(jobId);
        if (job == null) {
            System.out.println("Invalid job ID.");
            return;
        }

        List<CV> cvs = storage.getAllCVs();
        cvs.sort(Comparator.comparingDouble(cv -> -matcher.calculateMatch(cv, job)));

        System.out.println("Ranked Candidates:");
        for (CV cv : cvs) {
            double score = matcher.calculateMatch(cv, job);
            System.out.printf("- %s: %.2f%%%n", cv.getEmail(), score);
        }
    }

    private void addJobPosting() {
        System.out.println();
        System.out.println("==========================");
        System.out.println("  Add a New Job Posting");
        System.out.println("==========================");

        System.out.print("Enter job title: ");
        String title = scanner.nextLine();
        System.out.print("Enter company: ");
        String company = scanner.nextLine();
        System.out.print("Enter location: ");
        String location = scanner.nextLine();
        System.out.print("Enter job description: ");
        String description = scanner.nextLine();
        System.out.print("Enter required skills (comma-separated): ");
        List<String> skills = Arrays.stream(scanner.nextLine().split(","))
                                    .map(String::trim)
                                    .toList();

        storage.addJob(new Job(title, company, location, description, skills));
        System.out.println("Job added.");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}
