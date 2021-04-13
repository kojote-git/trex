package com.jkojote.trex.place.api.model.response

import com.jkojote.trex.place.domain.model.Location
import com.jkojote.trex.place.domain.model.Place
import com.jkojote.trex.place.domain.model.ResourceId

data class DetailedPlaceDto(
  val id: String,
  val name: String,
  val description: String,
  val category: String,
  val region: String,
  val location: Location,
  val thumbnail: ResourceId?,
  val photos: List<ResourceId>
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