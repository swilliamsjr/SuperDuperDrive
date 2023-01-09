package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private Logger logger = LoggerFactory.getLogger(NoteController.class);

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String noteSubmit(
            @ModelAttribute("Note") Note note,
            Authentication authentication,
            Model model
    ) {

        String username = authentication.getPrincipal().toString();

        this.logger.error("Submitted Note: " + note.toString());

        int isSuccess;
        isSuccess = this.noteService.insertNote(note);

        return "redirect:/result?isSuccess=" + isSuccess;
    }

    @GetMapping("/note")
    public String noteDeletion(
            @ModelAttribute("Note") Note note,
            @RequestParam(required = false, name = "noteId") Integer noteId,
            Authentication authentication,
            Model model
    ) {
        String username = authentication.getPrincipal().toString();

        this.logger.error(noteId.toString());

        Boolean isSuccess = this.noteService.deleteNote(noteId, username);

        return "redirect:/result?isSuccess=" + isSuccess;
    }
}