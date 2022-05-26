package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.NoteException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.NoteDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: Heng Yu
 */
@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }


    public List<Note> getAllNotesByUserId(Integer userId) {
        return noteMapper.findAllByUserId(userId);
    }

    /**
     * Add new note into db
     * @param noteDto
     * @param loginUsername
     * @return
     */
    public Integer addNote(NoteDto noteDto, String loginUsername) {
        Note newNote = new Note();
        BeanUtils.copyProperties(noteDto, newNote);

        Integer loginUserId = userService.getLoginUserId(loginUsername);
        newNote.setUserId(loginUserId);

        Integer result = noteMapper.insertNote(newNote);
        if (result == null) {
            throw new NoteException("Can not add the note!");
        }

        return result;
    }

    /**
     * Modify the note
     * @param noteDto
     * @param loginUsername
     * @return
     */
    public Integer modifyNote(NoteDto noteDto, String loginUsername) {
        Integer loginUserId = userService.getLoginUserId(loginUsername);
        Note note = noteMapper.findNoteById(noteDto.getNoteId(), loginUserId).orElseThrow(() -> new NoteException("Can not find the note"));

        BeanUtils.copyProperties(noteDto, note);

        Integer result = noteMapper.updateNoteById(note, loginUserId);
        if (result == null) {
            throw new NoteException("Can not modify the note");
        }
        return result;
    }

    /**
     * Delete the note
     * @param noteId
     * @param loginUsername
     * @return the note need to be deleted
     */
    public Note deleteNote(Integer noteId, String loginUsername){
        Integer loginUserId = userService.getLoginUserId(loginUsername);

        Note need2Delete = noteMapper.findNoteById(noteId,loginUserId).orElseThrow(()->new NoteException("Can not find the note"));

        Integer result = noteMapper.deleteNoteById(noteId);
        if(result == null){
            throw new NoteException("The note can not be removed");
        }

        return need2Delete;
    }
}
