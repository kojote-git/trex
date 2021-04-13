package com.jkojote.trex.place.api.model.request

import com.jkojote.trex.place.domain.model.Location

data class FindNearestPlacesRequestDto(
  val location: Location,
  val distanceInMeters: Long
)