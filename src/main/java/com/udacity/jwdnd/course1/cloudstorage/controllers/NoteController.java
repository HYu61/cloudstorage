package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.NoteException;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * author: Heng Yu
 */
@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public String addOrModifyNote(NoteDto noteDto, Authentication authentication, RedirectAttributes redirectAttributes) {
        String loginUsername = authentication.getName();
        if (noteDto.getNoteId() == null) { // add new note
            noteService.addNote(noteDto, loginUsername);
            redirectAttributes.addFlashAttribute("noteMsg", String.format("Note -- %s is created!", noteDto.getNoteTitle()));
        } else { // modify the note
            System.out.println(noteDto.getNoteId());
            noteService.modifyNote(noteDto, loginUsername);
            redirectAttributes.addFlashAttribute("noteMsg", String.format("Note -- %s is edited", noteDto.getNoteTitle()));

        }
        return "redirect:/home";
    }

    @GetMapping("/delete/noteId")
    public String deleteNote(@RequestParam Integer noteId, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (noteId == null) {
            throw new NoteException("No such note");
        }
        String loginUsername = authentication.getName();
        Note result = noteService.deleteNote(noteId, loginUsername);
        redirectAttributes.addFlashAttribute("noteMsg", String.format("Note -- %s is removed", result.getNoteTitle()));

        return "redirect:/home";
    }
}
