package com.infoshare.webapp.controller;

import com.infoshare.webapp.service.FileJSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class UploadController {

    @Autowired
    FileJSONService fileJSONService;

    @PostMapping("/upload")
    public String loadFile(Model model, @RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {
        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }
        fileJSONService.loadFile(file);
        model.addAttribute("file",file);
        attributes.addFlashAttribute("message", "You successfully uploaded " + file.getName() + '!');

        return "redirect:/";
    }

}

