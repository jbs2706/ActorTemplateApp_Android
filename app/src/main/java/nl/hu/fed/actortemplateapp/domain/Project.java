package nl.hu.fed.actortemplateapp.domain;

public class Project {

    private String title, description, analist;
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

    public String getAnalist() {
        return analist;
    }

    public void setAnalist(String analist) {
        this.analist = analist;
    }
}
