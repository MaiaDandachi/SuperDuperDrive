package com.udacity.jwdnd.course1.cloudstorage.contoller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/home/notes")
@Controller
public class NoteController {

    private NoteService noteService;
    private UserMapper userMapper;

    public NoteController(NoteService noteService, UserMapper userMapper) {
        this.noteService = noteService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public String handleAddUpdateNote(Authentication authentication, Note note){
        String loggedInUserName = (String) authentication.getPrincipal();
        User user = userMapper.getUser(loggedInUserName);
        Integer userId = user.getUserId();

        if (note.getNoteid() != null) {
            noteService.updateNote(note);
        } else {
            noteService.addNote(note, userId);
        }

        return "redirect:/result?success";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("id") int noteid, RedirectAttributes redirectAttributes){
        if(noteid > 0){
            noteService.deleteNote(noteid);
            return "redirect:/result?success";
        }


        redirectAttributes.addAttribute("error", "Unable to delete the note.");
        return "redirect:/result?error";
    }
}
