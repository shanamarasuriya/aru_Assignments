//Student ID- 2370967
package model;

import java.util.ArrayList;
import java.util.List;

// * A simple in-memory data store for jobs and CVs.
// * Acts as the temporary "database" during runtime.

public class DataStorage {
    private final List<Job> jobs = new ArrayList<>();
    private final List<CV> cvs = new ArrayList<>();

    //Adds a job posting to the system.
    public void addJob(Job job) {
        jobs.add(job);
    }

    public List<Job> getAllJobs() {
        return jobs;
    }

    //Returns the job at the given index, or null if out of bounds.
    public Job getJob(int index) {
        if (index < 0 || index >= jobs.size()) return null;
        return jobs.get(index);
    }

    //Adds a candidate CV to the system.
    public void addCV(CV cv) {
        cvs.add(cv);
    }

    //Returns a list of all stored CVs.
    public List<CV> getAllCVs() {
        return cvs;
    }
}
