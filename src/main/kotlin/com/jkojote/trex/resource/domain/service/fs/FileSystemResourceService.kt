package com.jkojote.trex.resource.domain.service.fs

import com.jkojote.trex.resource.domain.model.Content
import com.jkojote.trex.resource.domain.model.Resource
import com.jkojote.trex.resource.domain.service.CreateResourceInput
import com.jkojote.trex.resource.domain.service.ResourceRepository
import com.jkojote.trex.resource.domain.service.ResourceService
import org.springframework.core.io.FileSystemResource
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

@Transactional
class FileSystemResourceService(
  private val resourceRepository: ResourceRepository,
  private val directory: File
) : ResourceService {

  override fun createResource(input: CreateResourceInput) : Resource {
    val resource = Resource(
      id = UUID.randomUUID().toString(),
      contentType = input.contentType
    )
    return resourceRepository.save(resource)
  }

  override fun deleteResource(resource: Resource) {
    resourceRepository.delete(resource)
  }

  override fun saveContent(resource: Resource, content: InputStream) {
    val file = getFile(resource)
    writeContent(file, content)
  }

  override fun getContent(resource: Resource): Optional<Content> {
    val file = getFile(resource)
    if (!file.exists()) {
      return Optional.empty()
    }
    return Optional.of(FileSystemResource(file))
  }

  override fun findResource(resourceId: String): Optional<Resource> {
    return resourceRepository.findById(resourceId)
  }

  private fun getFile(resource: Resource): File {
    return File(directory, resource.id)
  }

  private fun writeContent(file: File, content: InputStream) {
    FileOutputStream(file).use {
      content.transferTo(it)
    }
  }

}