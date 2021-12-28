package com.udacity.jwdnd.course1.cloudstorage.contoller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/home/files")
public class FileController {

    private FileService fileService;
    private UserMapper userMapper;

    public FileController(FileService fileService, UserMapper userMapper) {
        this.fileService = fileService;
        this.userMapper = userMapper;
    }


    @PostMapping
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        String uploadError = null;

        String loggedInUserName = (String) authentication.getPrincipal();
        User user = userMapper.getUser(loggedInUserName);

        if (fileUpload.isEmpty()) {
            uploadError = "Please select a non-empty file.";
        }

        if (!fileService.isFileAvailable(fileUpload.getOriginalFilename(), user.getUserId())) {
            uploadError = "The file already exists.";

        }

        if(uploadError!=null) {
            redirectAttributes.addFlashAttribute("error", uploadError);
            return "redirect:/result?error";
        }

        fileService.addFile(fileUpload, user.getUserId());
        return "redirect:/result?success";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("id") int fileid, Authentication authentication, RedirectAttributes redirectAttributes){
        String loggedInUserName = (String) authentication.getPrincipal();
        User user = userMapper.getUser(loggedInUserName);
        String deleteError = null;

        if(fileid > 0){
            fileService.deleteFile(fileid);
            return "redirect:/result?success";
        }


        redirectAttributes.addAttribute("error", "Unable to delete the file.");
        return "redirect:/result?error";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ file.getFilename()+"\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }

}
