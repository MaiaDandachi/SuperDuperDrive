package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes(int userid){
        return noteMapper.getNotes(userid);
    }

    public void addNote(Note note, int userId){
        Note newNote = new Note();
        newNote.setUserid(userId);
        newNote.setNotedescription(note.getNotedescription());
        newNote.setNotetitle(note.getNotetitle());

        noteMapper.insertNote(newNote);
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public void deleteNote(int noteid){
        noteMapper.deleteNote(noteid);
    }
}
