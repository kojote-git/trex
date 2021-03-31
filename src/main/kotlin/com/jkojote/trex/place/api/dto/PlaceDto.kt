package com.jkojote.trex.place.api.dto

import com.jkojote.trex.place.domain.model.Location
import com.jkojote.trex.place.domain.model.Place

data class PlaceDto(
  val id: String,
  val name: String,
  val description: String,
  val category: String,
  val region: String,
  val location: Location
) {

  companion object {
    fun fromPlace(place: Place) : PlaceDto {
      return PlaceDto(
        id = place.id,
        name = place.name,
        description = place.description,
        category = place.category,
        region = place.region,
        location = place.location
      )
    }
  }
}