package com.jkojote.trex.history.api.dto

import com.jkojote.trex.history.domain.model.History
import com.jkojote.trex.history.domain.model.SavedPlace

data class HistoryDto(
  val userId: Long,
  val savedPlaces: List<SavedPlaceDto>
) {
  companion object {
    fun fromHistory(history: History) : HistoryDto {
      return HistoryDto(
        userId = history.userId.id,
        savedPlaces = history.places.map { SavedPlaceDto.fromSavedPlace(it) }
      )
    }
  }
}