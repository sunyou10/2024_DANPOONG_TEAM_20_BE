package com.example.mixmix.s3.util;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record S3Util(AmazonS3 amazonS3) {

    @Value("${cloud.aws.s3.bucket}")
    private static String bucket;

    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
