//package com.cloudstorage.controller;
//
//import com.cloudstorage.model.Note;
//import com.cloudstorage.model.User;
//import com.cloudstorage.service.CredentialService;
//import com.cloudstorage.service.FileService;
//import com.cloudstorage.service.NoteService;
//import com.cloudstorage.service.UserService;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/home")
//public class NotesController {
//    private NoteService noteService;
//    private FileService fileService;
//    private CredentialService credentialService;
//    private UserService userService;
//
//    public NotesController(NoteService noteService, FileService fileService, CredentialService credentialService, UserService userService) {
//        this.noteService = noteService;
//        this.fileService = fileService;
//        this.credentialService = credentialService;
//        this.userService = userService;
//    }
//    @PostMapping
//    public String postOrEditNote(Authentication authentication, Note note, Model model) {
//        User currentUser =  this.userService.getUser(authentication.getName());
//        note.setUserId(currentUser.getUserId());
//        if (note.getNoteId() < 0) {
//            try{noteService.createNote(new Note(null, note.getNoteTitle(),
//                    note.getNoteDescription(), note.getUserId()));}
//            catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
//        } else{
//            try{ noteService.editNote(note);}
//            catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
//        }
//        model.addAttribute("notes", this.noteService.getAll());
//        return "home";
//    }
//
//    @PostMapping(value = "/delete/{id}")
//    public String deleteNote(@PathVariable String id, Model model) {
//        try{this.noteService.deleteNote(id);}
//        catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
//        model.addAttribute("notes", this.noteService.getAll());
//        return "result";
//    }
//}
