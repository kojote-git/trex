package com.jkojote.trex.history.api.dto

import com.jkojote.trex.history.domain.model.SavedPlace
import java.time.ZonedDateTime

data class SavedPlaceDto(
  val placeId: String,
  val date: ZonedDateTime
) {
  companion object {
    fun fromSavedPlace(savedPlace: SavedPlace) : SavedPlaceDto {
      return SavedPlaceDto(
        placeId = savedPlace.placeId.id,
        date = savedPlace.date
      )
    }
  }
}