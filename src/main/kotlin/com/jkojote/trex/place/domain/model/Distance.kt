package com.jkojote.trex.place.domain.model

data class Distance(
  val value: Long,
  val unit: Unit
) {

  enum class Unit {
    METER
  }
}