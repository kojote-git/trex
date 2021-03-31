package com.jkojote.trex.place.domain.service

import com.jkojote.trex.place.domain.model.Location
import javax.validation.constraints.NotEmpty

data class CreatePlaceInput(

  @NotEmpty
  val name: String,
  val description: String,
  val category: String,
  val region: String,
  val location: Location,
)