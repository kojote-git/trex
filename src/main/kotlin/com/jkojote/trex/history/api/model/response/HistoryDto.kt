package com.jkojote.trex.history.api.model.response

import com.jkojote.trex.history.domain.model.History

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