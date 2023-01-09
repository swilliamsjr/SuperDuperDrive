package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.AppUser;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    UserService userService;
    CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/save")
    public String createCredential(Authentication auth, Credential credential, Model model) {
        AppUser user = (AppUser) auth.getPrincipal();
        if (credential.getCredentialId() > 0) {
            credentialService.updateCredential(credential);
        }
        else {
            credentialService.addCredential(credential, AppUser.getUserId());
        }
        return "redirect:/result?success";
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("id")int id) {
        boolean isSuccess = id > 0;
        if(isSuccess) {
            credentialService.deleteCredential(id);
        }
        return "redirect:/result?error";
    }
}
