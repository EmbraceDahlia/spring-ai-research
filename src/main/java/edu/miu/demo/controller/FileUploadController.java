package edu.miu.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            Path path = Paths.get("uploads/" + filename);
            Files.createDirectories(path.getParent()); // Ensure the directory exists
            Files.write(path, file.getBytes());

            return ResponseEntity.ok(filename);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }
}
