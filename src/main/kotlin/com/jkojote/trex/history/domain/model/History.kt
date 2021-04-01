package com.jkojote.trex.history.domain.model

import javax.persistence.Id

data class History(

  @Id
  val userId: UserId,
  val places: List<SavedPlace>
)