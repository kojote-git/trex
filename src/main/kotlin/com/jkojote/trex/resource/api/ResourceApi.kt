package com.jkojote.trex.resource.api

import com.jkojote.trex.resource.domain.model.Content
import com.jkojote.trex.resource.domain.model.Resource
import com.jkojote.trex.resource.domain.service.ResourceService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/resource"])
class ResourceApi(
  private val resourceService: ResourceService
) {

  @GetMapping("{id}")
  fun getContent(@PathVariable("id") id: String) : ResponseEntity<Content> {
    val resource = resourceService.findResource(id)
    if (resource.isEmpty) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    val content = resourceService.getContent(resource.get())
    if (content.isEmpty || !content.get().exists()) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    return contentResponse(resource.get(), content.get())
  }

  private fun contentResponse(resource: Resource, content: Content) : ResponseEntity<Content> {
    val headers = HttpHeaders().apply {
      set(HttpHeaders.CONTENT_TYPE, resource.contentType)
      set(HttpHeaders.CONTENT_LENGTH, "${content.contentLength()}")
    }
    return ResponseEntity(content, headers, HttpStatus.OK)
  }
}