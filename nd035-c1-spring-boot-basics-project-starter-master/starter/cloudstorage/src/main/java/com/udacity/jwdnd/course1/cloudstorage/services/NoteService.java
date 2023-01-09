package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(int userId){
        return noteMapper.getAllNotes(userId);
    }

    public int insertNote(Note note) {
        int id;
        if(note.getNoteId() == null) {
            id = noteMapper.insertNote(note);
        } else {
            id = noteMapper.updateNote(note);
        }
        return id;
    }

    public Boolean deleteNote(int id, String username) {
        noteMapper.deleteNote(id);
        return null;
    }
}
