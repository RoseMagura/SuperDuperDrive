package com.cloudstorage.service;

import org.springframework.stereotype.Service;
import com.cloudstorage.model.Note;
import com.cloudstorage.mapper.NoteMapper;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

     public int createNote(Note note) { return noteMapper.createNote(note);}
     public List<Note> getAll(){return noteMapper.getAllNotes();}

     public Note getNote(String noteId){ return noteMapper.getNote(noteId);}

    // public Note editNote() {}

     public void deleteNote(String noteId) {noteMapper.deleteNote(noteId);}
}
