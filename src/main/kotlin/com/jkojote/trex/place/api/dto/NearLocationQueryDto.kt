package com.jkojote.trex.place.api.dto

import com.jkojote.trex.place.domain.model.Location

class NearLocationQueryDto(
  val location: Location,
  val distanceInMeters: Long
)