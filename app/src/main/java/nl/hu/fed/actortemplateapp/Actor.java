package nl.hu.fed.actortemplateapp;

import java.util.ArrayList;

/**
 * Created by jonah-pc on 20-Mar-17.
 */

public class Actor {
    private String rolename, taskdescription, projectKey;
    private ArrayList<Person> actorPersons;
    private boolean archived;
    String key;

    public ArrayList<Person> getActorPersons() {
        return actorPersons;
    }

    public void setActorPersons(ArrayList<Person> actorPersons) {
        this.actorPersons = actorPersons;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getTaskdescription() {
        return taskdescription;
    }

    public void setTaskdescription(String taskdescription) {
        this.taskdescription = taskdescription;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getProjectKey() {
        return projectKey;
    }
}
