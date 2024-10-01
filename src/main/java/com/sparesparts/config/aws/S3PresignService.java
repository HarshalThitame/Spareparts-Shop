package com.sparesparts.config.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.Duration;


@Service
public class S3PresignService {

    private final S3Presigner s3Presigner;

    public S3PresignService(S3Presigner s3Presigner) {
        this.s3Presigner = s3Presigner;
    }


    public String generatePresignedUrl(String bucketName, String objectKey) {
        objectKey = "SpareParts" + "/" + objectKey; // Specify the folder path in the S3 bucket
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest)
                .signatureDuration(Duration.ofMinutes(10)) // URL expires in 10 minutes
                .build();

        return s3Presigner.presignPutObject(presignRequest).url().toString();
    }
}
