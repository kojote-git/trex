package com.jkojote.trex.config.services

import com.jkojote.trex.resource.domain.service.ResourceRepository
import com.jkojote.trex.resource.domain.service.ResourceService
import com.jkojote.trex.resource.domain.service.s3.S3ResourceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
@Profile("enable-s3")
class S3ResourceServiceConfig {

  @Value("\${aws.s3.access-key}")
  private lateinit var accessKey: String

  @Value("\${aws.s3.access-key-id}")
  private lateinit var accessKeyId: String

  @Value("\${aws.s3.region}")
  private lateinit var region: String

  @Value("\${aws.s3.bucket}")
  private lateinit var bucket: String

  @Value("\${resources.temp-dir}")
  private lateinit var tempDir: String

  @Autowired
  private lateinit var resourceRepository: ResourceRepository

  @Bean
  fun s3Client() : S3Client {
    return S3Client.builder()
      .credentialsProvider { AwsBasicCredentials.create(accessKeyId, accessKey) }
      .region(Region.of(region))
      .build()
  }

  @Bean
  fun s3ResourceService() : ResourceService {
    return S3ResourceService(
      resourceRepository = resourceRepository,
      s3Bucket = bucket,
      s3Client = s3Client(),
      tempDir = tempDir
    )
  }
}