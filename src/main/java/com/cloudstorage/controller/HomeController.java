package com.cloudstorage.controller;

import com.cloudstorage.service.*;
import com.cloudstorage.model.*;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@Controller
@RequestMapping("/home")
public class HomeController {
    private NoteService noteService;
    private FileService fileService;
    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;
//    private final static Logger logger = Logger.getLogger(HomeController.class.getName());

    public HomeController(NoteService noteService, FileService fileService, CredentialService credentialService,
                          EncryptionService encryptionService, UserService userService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getHomePage(Model model, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        // get values stored in database
        modelAndView.addObject("notes", this.noteService.getAll());
//        modelAndView.addObject("files", this.fileService.getAll());
        modelAndView.addObject("credentials", this.credentialService.getAll());
        return modelAndView;
    }
    @PostMapping(value = "/credential/new")
    public String postOrEditCredential(Authentication authentication, Credential credential, Model model) {
        User currentUser =  this.userService.getUser(authentication.getName());
        credential.setUserId(currentUser.getUserId());
//        if (credential.getCredentialId() == 0 || credential.getCredentialId() == null) {
//            try{
                credentialService.createCredential(new Credential(null, credential.getUrl(),
                        credential.getUsername(), currentUser.getSalt(), this.encryptionService.encryptValue(credential.getPassword(), currentUser.getSalt()),
                        credential.getUserId()));
//            }
//            catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
//        } else{
//            try{ credentialService.editCredential(credential);}
//            catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
//        }
        model.addAttribute("credentials", this.credentialService.getAll());
        return "redirect:/result";
    }
    @PostMapping(value = "/note/new")
    public String postOrEditNote(Authentication authentication, Note note, Model model) {
        User currentUser =  this.userService.getUser(authentication.getName());
        note.setUserId(currentUser.getUserId());
        if (note.getNoteId() == 0) {
            try{
                noteService.createNote(new Note(null, note.getNoteTitle(),
                    note.getNoteDescription(), note.getUserId()));

            }
            catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
        } else{
            try{ noteService.editNote(note);}
            catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
        }
        model.addAttribute("notes", this.noteService.getAll());
        return "redirect:/result";
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteNote(@PathVariable String id, Model model) {
        try{this.noteService.deleteNote(id);}
        catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
        model.addAttribute("notes", this.noteService.getAll());
        return "redirect:/result";
    }
//    @PostMapping


    @PostMapping(value = "/delete/credential/{id}")
    public String deleteCredential(@PathVariable String id, Model model) {
        try{this.credentialService.deleteCredential(id);}
        catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
        model.addAttribute("credentials", this.credentialService.getAll());
        return "redirect:/result";

        //FILES
//        @PostMapping
//        public String postOrEditFile(Authentication authentication, File file, Model model) {
//            User currentUser =  this.userService.getUser(authentication.getName());
//            // may need to edit this line
//            file.setUserId(currentUser.getUserId());
//            if (file.getFileId() == 0) {
//                try{
//                    // need to edit this line
//                    fileService.createFile(new File(null, file.getFileTitle(),
//                            file.getFileDescription(), file.getUserId()));
//
//                }
//                catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
//            } else{
//                try{ fileService.editFile(file);}
//                catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
//            }
//            model.addAttribute("files", this.fileService.getAll());
//            return "home";
//        }
//
//        @PostMapping(value = "/delete/{id}")
//        public String deleteFile(@PathVariable String id, Model model) {
//            try{this.fileService.deleteFile(id);}
//            catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
//            model.addAttribute("files", this.fileService.getAll());
//            return "result";
//        }
    }
}
