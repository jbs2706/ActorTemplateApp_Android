package nl.hu.fed.actortemplateapp;

import java.util.ArrayList;

public class Project {

    private String title, description;
    private ArrayList<Actor> projectActors;
    String key;

    public ArrayList<Actor> getProjectActors() {
        return projectActors;
    }

    public void setProjectActors(ArrayList<Actor> projectActors) {
        this.projectActors = projectActors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
