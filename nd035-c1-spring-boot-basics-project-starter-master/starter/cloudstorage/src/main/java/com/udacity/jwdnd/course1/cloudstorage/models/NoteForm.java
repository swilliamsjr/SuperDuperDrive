package com.udacity.jwdnd.course1.cloudstorage.models;


public class NoteForm {
    private String title;
    private String description;

    private String noteId;

    public String getNoteId(){
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
