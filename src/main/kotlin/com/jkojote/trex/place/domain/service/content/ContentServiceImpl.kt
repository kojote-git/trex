package com.jkojote.trex.place.domain.service.content

import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

@Service
class ContentServiceImpl(
  private val directory: File
) : ContentService {

  override fun saveContent(content: InputStream): ContentId {
    val contentId = nextId()
    val file = getFile(contentId)
    writeContent(file, content)
    return contentId
  }

  override fun deleteContent(contentId: ContentId) {
    getFile(contentId).delete()
  }

  override fun getContent(contentId: ContentId): Optional<Resource> {
    val file = getFile(contentId)
    if (file.exists()) {
      return Optional.of(FileSystemResource(file))
    } else {
      return Optional.empty()
    }
  }

  private fun writeContent(file: File, content: InputStream) {
    FileOutputStream(file).use {
      content.transferTo(it)
    }
  }

  private fun getFile(contentId: ContentId) : File {
    return File(directory, contentId.value)
  }

  private fun nextId() : ContentId {
    return ContentId(UUID.randomUUID().toString())
  }
}