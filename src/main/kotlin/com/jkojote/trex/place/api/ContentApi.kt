package com.jkojote.trex.place.api

import com.jkojote.trex.place.domain.service.content.ContentId
import com.jkojote.trex.place.domain.service.content.ContentService
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/content"])
class ContentApi(
  private val contentService: ContentService
) {

  @GetMapping("{id}")
  fun getContent(@PathVariable("id") contentId: String) : ResponseEntity<Resource> {
    val content = contentService.getContent(ContentId(contentId))
    if (content.isEmpty) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    }
    return ResponseEntity(content.get(), HttpStatus.OK)
  }

}