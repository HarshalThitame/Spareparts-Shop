package com.sparesparts.config.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class PresignedUrlController {
    private final S3Service s3Service;
    private final S3PresignService s3PresignService;

    @Autowired
    public PresignedUrlController(S3Service s3Service, S3PresignService s3PresignService) {
        this.s3Service = s3Service;
        this.s3PresignService = s3PresignService;
    }

    @GetMapping("/presigned-url/{folderName}")
    public Map<String, String> generatePresignedUrl(@PathVariable String folderName) {
        // Initialize the S3 presigner
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create("AKIAYRH5ND6JDPJFGWEN", "RWySO+RJaU9LTjBU0NYs8lMhcfrZvbWpxjeO9UBl");
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();

        // Define the object key (i.e., the file name) in S3
        String objectKey = "SpareParts/"+folderName+"/image-" + System.currentTimeMillis() + ".jpg";

        // Create a PutObjectRequest to define the S3 object details
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket("harshal-ecom")
                .key(objectKey)
                .build();

        // Create a PutObjectPresignRequest with an expiration time for the URL
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(objectRequest)
                .signatureDuration(Duration.ofMinutes(10))  // URL will be valid for 10 minutes
                .build();

        // Generate the presigned URL
        URL presignedUrl = presigner.presignPutObject(presignRequest).url();

        // Return the presigned URL in a response
        Map<String, String> response = new HashMap<>();
        response.put("url", presignedUrl.toString());
        response.put("key", objectKey);

        return response;
    }

    @DeleteMapping("/delete-file")
    public ResponseEntity<String> deleteFile(@RequestParam String key) {
        s3Service.deleteFile(key);
        return ResponseEntity.ok("File deleted successfully");
    }
}
