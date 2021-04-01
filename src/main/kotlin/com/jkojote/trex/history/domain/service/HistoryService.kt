package com.jkojote.trex.history.domain.service

import com.jkojote.trex.history.domain.model.History
import com.jkojote.trex.history.domain.model.PlaceId
import com.jkojote.trex.history.domain.model.UserId
import java.util.*

interface HistoryService {

  fun createHistory(userId: UserId) : History

  fun findHistory(userId: UserId) : Optional<History>

  fun addSavedPlace(history: History, placeId: PlaceId) : History

  fun removeSavedPlace(history: History, placeId: PlaceId) : History
}