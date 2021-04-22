package com.jkojote.trex.history.api

import com.jkojote.trex.jwt.UserAuthentication
import com.jkojote.trex.history.domain.model.History
import com.jkojote.trex.history.domain.model.PlaceId
import com.jkojote.trex.history.domain.model.UserId
import com.jkojote.trex.history.domain.service.HistoryService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class HistoryServiceFacade(private val historyService: HistoryService) {

  fun savePlace(placeId: String, authentication: Authentication) {
    val history = getHistory(authentication)
    historyService.addSavedPlace(history, PlaceId(placeId))
  }

  fun unsavePlace(placeId: String, authentication: Authentication) {
    val history = getHistory(authentication)
    historyService.removeSavedPlace(history, PlaceId(placeId))
  }

  fun getHistory(authentication: Authentication) : History {
    return getHistory(getUserId(authentication))
  }

  private fun getHistory(userId: UserId) : History {
    return historyService
      .findHistory(userId)
      .orElseGet { historyService.createHistory(userId) }
  }

  private fun getUserId(authentication: Authentication) : UserId {
    val id = (authentication as UserAuthentication).principal.id
    return UserId(id!!)
  }

}