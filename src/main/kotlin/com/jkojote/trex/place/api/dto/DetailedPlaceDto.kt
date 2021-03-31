package com.jkojote.trex.place.api.dto

import com.jkojote.trex.place.domain.model.Image
import com.jkojote.trex.place.domain.model.Location
import com.jkojote.trex.place.domain.model.Place

data class DetailedPlaceDto(
  val id: String,
  val name: String,
  val description: String,
  val category: String,
  val region: String,
  val location: Location,
  val thumbnail: Image?,
  val photos: List<Image>
) {

  companion object {
    fun fromPlace(place: Place) : DetailedPlaceDto {
      return DetailedPlaceDto(
        id = place.id,
        name = place.name,
        description = place.description,
        category = place.category,
        region = place.region,
        location = place.location,
        thumbnail = place.thumbnail,
        photos = place.photos
      )
    }
  }
}