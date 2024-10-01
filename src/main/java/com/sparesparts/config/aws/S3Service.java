package com.sparesparts.config.aws;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class S3Service {

    private final S3Client s3Client;

    public S3Service() {
        this.s3Client = S3Client.builder()
                .region(Region.EU_NORTH_1) // Replace with your actual region
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("AKIAYRH5ND6JDPJFGWEN", "RWySO+RJaU9LTjBU0NYs8lMhcfrZvbWpxjeO9UBl")
                ))
                .build();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String bucketName = "harshal-ecom";

        String fileName = "SpareParts/Product-Main-Image/"+Paths.get(Objects.requireNonNull(file.getOriginalFilename())).getFileName().toString();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return "File uploaded: " + fileName;
    }

    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket("harshal-ecom")
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.EU_NORTH_1) // Replace with your AWS region
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                "AKIAYRH5ND6JDPJFGWEN",
                                "RWySO+RJaU9LTjBU0NYs8lMhcfrZvbWpxjeO9UBl"))
                )
                .build();
    }

}