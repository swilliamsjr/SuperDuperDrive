package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController<newCredential> {

    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private int fileName;

    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) throws Exception {
        Integer userId = getUserId(authentication);
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        model.addAttribute("note", noteService.getAllNotes(userId));
        model.addAttribute("credentials", credentialService.getAllCredentials(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.name();
        AppUser user = userService.getUser(userName);
        return user.getUserId();
    }

    @PostMapping
    public String newFile(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) throws IOException {
        String userName = authentication.name();
        AppUser user = userService.getUser(userName);
        Integer userId = user.getUserId();
        List<File> fileListings = fileService.getAllFiles(userId);
        MultipartFile multipartFile = newFile.getFile();
        String fileName = multipartFile.getOriginalFilename();
        boolean fileIsDuplicate = false;
        for (File fileListing: fileListings) {
            if (fileListing.equals(fileName)) {
                fileIsDuplicate = true;
                break;
            }
        }
        if (!fileIsDuplicate){
            fileService.insertFile(multipartFile, userId);
            model.addAttribute("result", "success");
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "You have tried to add a duplicate file.");
        }
        model.addAttribute("files", fileService.getAllFiles(userId));
        return "result";
    }

    @GetMapping(
            value = "/get-file/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile(@PathVariable int fileName) {
        this.fileName = fileName;
        return fileService.getFile(fileName).getFileData();
    }

    @GetMapping(value = "/delete-file/{fileName}")
    public String deleteFile(
            Authentication authentication, @PathVariable int fileName, @ModelAttribute("newFile") FileForm newLife,
            @ModelAttribute("newCredential") CredentialForm newCredential, @ModelAttribute("newNote") Note newNote,
            Model model) {
        fileService.deleteFile(fileName);
        Integer userId = getUserId(authentication);
        model.addAttribute("files", fileService.getAllFiles(userId));
        model.addAttribute("result", "success");

        return "result";
    }

}

