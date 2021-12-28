package com.udacity.jwdnd.course1.cloudstorage.contoller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    private CredentialsService credentialsService;
    private UserMapper userMapper;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, NoteService noteService, CredentialsService credentialsService, UserMapper userMapper, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.userMapper = userMapper;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        String loggedInUserName = (String) authentication.getPrincipal();
        User user = userMapper.getUser(loggedInUserName);

        model.addAttribute("files", fileService.getUploadedFiles(user.getUserId()));
        model.addAttribute("notes", noteService.getNotes(user.getUserId()));
        model.addAttribute("credentials", credentialsService.getCredentials(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @GetMapping("/result")
    public String result(){
        return "result";
    }
}
