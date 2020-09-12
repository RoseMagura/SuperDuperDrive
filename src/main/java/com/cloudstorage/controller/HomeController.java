package com.cloudstorage.controller;

import com.cloudstorage.service.*;
import com.cloudstorage.model.*;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
    public String getHomePage(Model model) {
        // get values stored in database
        model.addAttribute("notes", this.noteService.getAll());
        model.addAttribute("files", this.fileService.getAll());
//        System.out.println(this.fileService.getAll());
        model.addAttribute("credentials", this.credentialService.getAll());
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @GetMapping(value = "/download")
    public ResponseEntity downloadFile(@RequestParam String id){
            File newFile = fileService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + newFile.getFilename() + "\"").body(newFile.getFileData());
    }

    @PostMapping(value = "/credential/new")
    public String postOrEditCredential(Authentication authentication, Credential credential, Model model) {
        User currentUser =  this.userService.getUser(authentication.getName());
        credential.setUserId(currentUser.getUserId());
        credential.setKey(currentUser.getSalt());

        if (credential.getCredentialId() == 0) {
            try{
                credentialService.createCredential(new Credential(null, credential.getUrl(),
                        credential.getUsername(), currentUser.getSalt(), this.encryptionService.encryptValue(credential.getPassword(), currentUser.getSalt()),
                        credential.getUserId()));


            }
            catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
        } else{
            try{
                credential.setPassword(this.encryptionService.encryptValue(credential.getPassword(), currentUser.getSalt()));
                credentialService.editCredential(credential);}
            catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
        }
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

    @PostMapping(value = "/note/{id}/delete")
    public String deleteNote(@PathVariable String id, Model model) {
        try{this.noteService.deleteNote(id);}
        catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
        model.addAttribute("notes", this.noteService.getAll());
        return "redirect:/result";
    }
    @PostMapping(value = "/credential/{id}/delete")
    public String deleteCredential(@PathVariable String id, Model model) {
        try{this.credentialService.deleteCredential(id);}
        catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
        model.addAttribute("credentials", this.credentialService.getAll());
        return "redirect:/result";}

        @PostMapping(value = "/file/new")
        public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {
            User currentUser =  this.userService.getUser(authentication.getName());
            File file = new File(null, fileUpload.getOriginalFilename(),fileUpload.getContentType(), (int) fileUpload.getSize(), currentUser.getUserId(), fileUpload.getBytes());
            this.fileService.createFile(file);
            model.addAttribute("files", this.fileService.getAll());
            return "redirect:/result";
        }

        @PostMapping(value = "/file/{id}/delete")
        public String deleteFile(@PathVariable String id, Model model) {
            try{this.fileService.deleteFile(id);}
            catch (Exception e){System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());}
            model.addAttribute("files", this.fileService.getAll());
            return "redirect:/result";
        }



//        @PostMapping("/home/download/{id}")
//        public String downloadFile(@PathVariable String id) {
//            File file = fileService.getFile(id);
//            System.out.println(file.toString());
////            return ResponseEntity.ok()
////                    .contentType(MediaType.parseMediaType(file.getContentType()))
////                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
////                            + file.getFilename() + "\"").body(file.getFileData());
//            return "redirect:/result";
//    }
    }

