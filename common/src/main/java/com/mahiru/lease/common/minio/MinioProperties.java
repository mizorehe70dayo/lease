package com.mahiru.lease.common.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @className MinioProperties
 * @description MinioProperties
 * @author mahiru
 * @date 2024/10/27 12:12
 * @version v1.0.0
**/
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProperties {

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
