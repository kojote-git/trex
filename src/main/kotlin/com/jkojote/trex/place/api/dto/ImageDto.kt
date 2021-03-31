package com.jkojote.trex.place.api.dto

import com.jkojote.trex.place.domain.model.Image

data class ImageDto(
  val contentId: String,
  val contentType: String
) {
  companion object {
    fun fromImage(image: Image) : ImageDto {
      return ImageDto(contentId = image.contentId.value, contentType = image.contentType)
    }
  }
}