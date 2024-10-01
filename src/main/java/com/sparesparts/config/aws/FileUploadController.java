package com.sparesparts.config.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/files")
public class FileUploadController {

    private final S3Service s3Service;


    @Autowired
    public FileUploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }



//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        String fileName;
//        try {
//            fileName = s3Service.uploadFile(file);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("Error uploading file");
//        }
//        return ResponseEntity.status(200).body(fileName);
//    }
}
