package com.jkojote.trex.place.api.dto

import com.jkojote.trex.place.domain.model.Location

class SearchNearestDto(
  val location: Location,
  val distanceInMeters: Long
)