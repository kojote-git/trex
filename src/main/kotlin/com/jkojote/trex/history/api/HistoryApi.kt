package com.jkojote.trex.history.api

import com.jkojote.trex.history.api.dto.HistoryDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/history"], produces = ["application/json"])
class HistoryApi(private val historyServiceFacade: HistoryServiceFacade) {

  @PostMapping("saved/{placeId}")
  fun savePlace(@PathVariable("placeId") placeId: String,
                authentication: Authentication) : ResponseEntity<Any> {

    historyServiceFacade.savePlace(placeId, authentication)
    return ResponseEntity(HttpStatus.OK)
  }

  @DeleteMapping("saved/{placeId}")
  fun unsavePlace(@PathVariable("placeId") placeId: String,
                  authentication: Authentication) : ResponseEntity<Any> {

    historyServiceFacade.unsavePlace(placeId, authentication)
    return ResponseEntity(HttpStatus.OK)
  }

  @GetMapping
  fun getHistory(authentication: Authentication) : ResponseEntity<HistoryDto> {
    val history = historyServiceFacade.getHistory(authentication)
    val historyDto = HistoryDto.fromHistory(history)
    return ResponseEntity(historyDto, HttpStatus.OK)
  }
}