package nl.hu.fed.actortemplateapp;

/**
 * Created by jonah-pc on 20-Mar-17.
 */

public class Person {
    private String name, email, function, phonenumber, notes, photo, actorkey;
    String key;

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

    public String getActorkey() {
        return actorkey;
    }

    public void setActorkey(String actorkey) {
        this.actorkey = actorkey;
    }

}
