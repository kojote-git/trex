package com.jkojote.trex.resource.domain.service.s3

import org.springframework.core.io.AbstractResource
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectResponse
import software.amazon.awssdk.services.s3.model.NoSuchKeyException
import java.io.FileNotFoundException
import java.io.InputStream

class S3Resource(
  private val key: String,
  private val bucket: String,
  private val s3Client: S3Client
) : AbstractResource() {

  private val request = GetObjectRequest.builder()
    .key(key)
    .bucket(bucket)
    .build()

  private val objectMetadata: ObjectMetadata? by lazy { loadObjectMetadata() }

  override fun getInputStream() : InputStream {
    try {
      return s3Client.getObject(request)
    } catch (e: NoSuchKeyException) {
      throw FileNotFoundException("Couldn't get resource by key: $key from bucket $bucket")
    }
  }

  override fun contentLength() : Long {
    return objectMetadata?.contentLength ?: 0
  }

  override fun exists(): Boolean {
    return objectMetadata != null
  }

  override fun getDescription() : String {
    return "$bucket://$key"
  }

  private fun loadObjectMetadata() : ObjectMetadata? {
    try {
      return s3Client.getObject(request).use { ObjectMetadata(it.response()) }
    } catch (e: NoSuchKeyException) {
      return null
    }
  }

  private data class ObjectMetadata(val response: GetObjectResponse) {
    val contentLength: Long = response.contentLength()
    val contentType: String = response.contentType()
  }
}