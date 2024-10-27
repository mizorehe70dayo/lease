package com.mahiru.lease.web.admin.service.impl;

import com.mahiru.lease.common.minio.MinioProperties;
import com.mahiru.lease.web.admin.service.FileService;
import io.minio.*;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String BUCKET_POLICY_TEMPLATE = """
            {
              "Statement": [{
                "Action": "s3:GetObject",
                "Effect": "Allow",
                "Principal": "*",
                "Resource": "arn:aws:s3:::%s/*"
              }],
              "Version": "2012-10-17"
            }
            """;

    private final MinioProperties properties;
    private final MinioClient client;

    @Override
    public String upload(MultipartFile file) throws FileUploadException,Exception {
        ensureBucketExists();

        String filename = generateFileName(file);
        uploadFileToMinio(file, filename);

        return String.join("/", properties.getEndpoint(), properties.getBucketName(), filename);
    }

    private void ensureBucketExists() throws Exception {
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(properties.getBucketName()).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(properties.getBucketName()).build());
            client.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(properties.getBucketName())
                    .config(createBucketPolicyConfig(properties.getBucketName()))
                    .build());
        }
    }

    private String generateFileName(MultipartFile file) {
        return String.format("%s/%s-%s",
                new SimpleDateFormat("yyyyMMdd").format(new Date()),
                UUID.randomUUID(),
                file.getOriginalFilename());
    }

    private void uploadFileToMinio(MultipartFile file, String filename) throws Exception {
        client.putObject(PutObjectArgs.builder()
                .bucket(properties.getBucketName())
                .object(filename)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());
    }

    private String createBucketPolicyConfig(String bucketName) {
        return String.format(BUCKET_POLICY_TEMPLATE, bucketName);
    }
}
