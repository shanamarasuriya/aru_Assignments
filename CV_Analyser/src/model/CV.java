//Student ID- 2370967
package model;

import java.util.List;

//Represents a candidate's CV with an email and a list of skills. So all the candidates gets an equal chance

public class CV {
    private String email;
    private List<String> skills;

    //Default constructor.
    public CV() {}

    //Creates a CV object with given email and skills.
    public CV(String email, List<String> skills) {
        this.email = email;
        this.skills = skills;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
