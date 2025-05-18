//Student ID- 2370967
package model;

import java.util.List;

//Represents a job posting with relevant details and required skills.
public class Job {
    private String title;
    private String company;
    private String location;
    private String description;
    private List<String> requiredSkills;

    //Constructs a Job object
    public Job(String title, String company, String location, String description, List<String> requiredSkills) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
        this.requiredSkills = requiredSkills;
    }

    public String getTitle() { return title; }
    public String getCompany() { return company; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public List<String> getRequiredSkills() { return requiredSkills; }
}
