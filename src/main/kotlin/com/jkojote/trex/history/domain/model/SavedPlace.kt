package com.jkojote.trex.history.domain.model

import java.time.ZonedDateTime

data class SavedPlace(
  val placeId: PlaceId,
  val date: ZonedDateTime
)