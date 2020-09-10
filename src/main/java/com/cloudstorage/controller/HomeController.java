package com.cloudstorage.controller;

import com.cloudstorage.service.CredentialService;
import com.cloudstorage.service.FileService;
import com.cloudstorage.service.NoteService;
import com.cloudstorage.model.*;
import com.cloudstorage.service.UserService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/home")
public class HomeController {
    private NoteService noteService;
    private FileService fileService;
    private CredentialService credentialService;
    private UserService userService;

    public HomeController(NoteService noteService, FileService fileService, CredentialService credentialService, UserService userService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.userService = userService;
    }
//    @RequestMapping("/home")
    @GetMapping
    public String getHomePage(Model model,Authentication authentication) {
        model.addAttribute("notes", this.noteService.getAll());
//        System.out.println(this.noteService.getAll());
        return "home";
    }
//    @RequestMapping("/home")
    @PostMapping
    public String postNote(Authentication authentication, Note note, Model model) {
        User currentUser =  this.userService.getUser(authentication.getName());
        note.setUserId(currentUser.getUserId());
        noteService.createNote(new Note(null, note.getNoteTitle(),
                note.getNoteDescription(), note.getUserId()));
        model.addAttribute("notes", this.noteService.getAll());
        return "home";
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteNote(@PathVariable String id, Model model) {
        this.noteService.deleteNote(id);
        model.addAttribute("notes", this.noteService.getAll());
        return "result";
    }
}
