package nl.hu.fed.actortemplateapp.domain;

/**
 * Created by jonah-pc on 20-Mar-17.
 */

public class Person {
    private String name, email, function, phonenumber, notes, photo, actorKey, analist;
    private boolean archived;
    public String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getActorKey() {
        return actorKey;
    }

    public void setActorKey(String actorKey) {
        this.actorKey = actorKey;
    }

    public String getAnalist() {
        return analist;
    }

    public void setAnalist(String analist) {
        this.analist = analist;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
