package com.jkojote.trex.resource.domain.service.s3

import com.jkojote.trex.resource.domain.model.Content
import com.jkojote.trex.resource.domain.model.Resource
import com.jkojote.trex.resource.domain.service.CreateResourceInput
import com.jkojote.trex.resource.domain.service.ResourceRepository
import com.jkojote.trex.resource.domain.service.ResourceService
import org.springframework.transaction.annotation.Transactional
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

@Transactional
class S3ResourceService(
  private val resourceRepository: ResourceRepository,
  private val s3Client: S3Client,
  private val s3Bucket: String,
  private val tempDir: String
) : ResourceService {

  override fun createResource(input: CreateResourceInput) : Resource {
    val resource = Resource(
      id = UUID.randomUUID().toString(),
      contentType = input.contentType
    )
    return resourceRepository.save(resource)
  }

  override fun deleteResource(resource: Resource) {
    val request = DeleteObjectRequest.builder()
      .bucket(s3Bucket)
      .key(resource.id)
      .build()
    s3Client.deleteObject(request)
  }

  override fun findResource(resourceId: String) : Optional<Resource> {
    return resourceRepository.findById(resourceId)
  }

  override fun saveContent(resource: Resource, content: InputStream) {
    val request = PutObjectRequest.builder()
      .bucket(s3Bucket)
      .key(resource.id)
      .contentType(resource.contentType)
      .build()
    val tempFile = saveTempFile(content)
    s3Client.putObject(request, RequestBody.fromFile(tempFile))
  }

  override fun getContent(resource: Resource) : Optional<Content> {
    return Optional.of(S3Resource(
      key = resource.id,
      bucket = s3Bucket,
      s3Client = s3Client
    ))
  }

  private fun saveTempFile(content: InputStream) : File {
    val file = File(tempDir, UUID.randomUUID().toString())
    FileOutputStream(file).use {
      content.transferTo(it)
    }
    return file
  }
}