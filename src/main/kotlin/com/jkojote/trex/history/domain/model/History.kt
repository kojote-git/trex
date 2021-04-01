package com.jkojote.trex.history.domain.model

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document(collection = "History")
data class History(

  @Id
  val userId: UserId,
  val places: List<SavedPlace>
)