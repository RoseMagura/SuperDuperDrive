package com.cloudstorage.service;

import org.springframework.stereotype.Service;
import com.cloudstorage.model.Note;
import com.cloudstorage.mapper.NoteMapper;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    // public int createNote(Note note) { return noteMapper.insert(note);}

     public Note getNote(String noteTitle){ return noteMapper.getNote(noteTitle);}

    // public Note editNote() {}

    // public void deleteNote(String noteId) {userMapper.deleteNote(noteId);}
}
