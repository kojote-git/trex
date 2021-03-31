package com.jkojote.trex.resource.domain.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Resource(

  @Id
  val id: String,

  @Column
  val contentType: String
)