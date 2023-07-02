package com.infoshare.webapp.controller;

import com.infoshare.webapp.service.FileJSONService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

@Controller
public class UploadController {

    private final FileJSONService fileJSONService;
    private static final Logger LOGGER = LogManager.getLogger(UploadController.class);

    public UploadController(FileJSONService fileJSONService) {
        this.fileJSONService = fileJSONService;
    }

    @PostMapping("/upload")
    public String loadFile(Model model, @RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {
        // check if file is empty
        if (file == null || file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            attributes.addFlashAttribute("messageType","danger");
            LOGGER.warn("No file to upload");
            return "redirect:/";
        }
        final String JSON_CONTENT_TYPE = "application/json";

        if (!Objects.equals(file.getContentType(), JSON_CONTENT_TYPE)) {
            attributes.addFlashAttribute("message", "Please select a file type JSON.");
            attributes.addFlashAttribute("messageType","warning");
            LOGGER.warn("Tried to upload different file than json");
            return "redirect:/";
        }
        fileJSONService.loadFile(file);
        attributes.addFlashAttribute("message", "You successfully uploaded " + file.getName() + '!');
        attributes.addFlashAttribute("messageType","success");
        return "redirect:/";
    }

}

