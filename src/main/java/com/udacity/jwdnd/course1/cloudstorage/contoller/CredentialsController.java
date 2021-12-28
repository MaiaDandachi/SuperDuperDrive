package com.udacity.jwdnd.course1.cloudstorage.contoller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/home/credentials")
@Controller
public class CredentialsController {
    private CredentialsService credentialsService;
    private UserMapper userMapper;

    public CredentialsController(CredentialsService credentialsService, UserMapper userMapper) {
        this.credentialsService = credentialsService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public String handleAddUpdateCredentials(Authentication authentication, Credentials credentials){
        String loggedInUserName = (String) authentication.getPrincipal();
        User user = userMapper.getUser(loggedInUserName);
        Integer userId = user.getUserId();

        if (credentials.getCredentialid() != null) {
            credentialsService.editCredentials(credentials);
        } else {
            credentialsService.addCredentials(credentials, userId);
        }

        return "redirect:/result?success";
    }

    @GetMapping("/delete")
    public String deleteCredentials(@RequestParam("id") int credentialid, Authentication authentication, RedirectAttributes redirectAttributes){
        String loggedInUserName = (String) authentication.getPrincipal();
        User user = userMapper.getUser(loggedInUserName);

        if(credentialid > 0){
            credentialsService.deleteCredentials(credentialid);
            return "redirect:/result?success";
        }


        redirectAttributes.addAttribute("error", "Unable to delete the credentials.");
        return "redirect:/result?error";
    }
}
