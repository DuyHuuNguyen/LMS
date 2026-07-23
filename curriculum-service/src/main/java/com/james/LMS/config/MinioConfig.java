package com.james.LMS.config;

import io.minio.MinioClient;
import java.net.URI;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

  private String url;
  private String accessKey;
  private String secretKey;
  private String videoBucket;
  private String thumbnailBucket;
  private String partSizeMB;

  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
  }

  @Bean
  public S3Client s3Client() {

    AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

    return S3Client.builder()
        .endpointOverride(URI.create("http://localhost:9000"))
        .region(Region.US_EAST_1)
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .forcePathStyle(true)
        .build();
  }
}
