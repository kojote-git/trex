package com.jkojote.trex.place.domain.model

import com.jkojote.trex.place.domain.service.content.ContentId

data class Image(
  val contentId: ContentId,
  val contentType: String
)