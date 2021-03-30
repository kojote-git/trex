package com.jkojote.trex.place.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Place")
data class Place(

  @Id
  val id: String,
  val name: String,
  val description: String,
  val category: String,
  val region: String,
  val location: Location,
  val thumbnail: Image?,
  val photos: List<Image>
)