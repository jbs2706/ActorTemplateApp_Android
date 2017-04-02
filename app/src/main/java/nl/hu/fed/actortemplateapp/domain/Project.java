package nl.hu.fed.actortemplateapp.domain;

import java.util.ArrayList;

import nl.hu.fed.actortemplateapp.domain.Actor;

public class Project {

    private String title, description;
    private boolean archived;
    public String key;

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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
