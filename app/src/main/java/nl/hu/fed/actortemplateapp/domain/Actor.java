package nl.hu.fed.actortemplateapp.domain;

/**
 * Created by jonah-pc on 20-Mar-17.
 */

public class Actor {
    private String rolename, taskdescription, projectKey, analist;
    private boolean archived;
    public String key;

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

    public String getAnalist() {
        return analist;
    }

    public void setAnalist(String analist) {
        this.analist = analist;
    }
}
