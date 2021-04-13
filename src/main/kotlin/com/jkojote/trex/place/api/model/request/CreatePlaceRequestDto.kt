package com.jkojote.trex.place.api.model.request

import com.jkojote.trex.place.domain.model.Location
import javax.validation.constraints.NotEmpty

class CreatePlaceRequestDto(
  @NotEmpty
  val name: String,
  val description: String,
  val category: String,
  val region: String,
  val location: Location
)