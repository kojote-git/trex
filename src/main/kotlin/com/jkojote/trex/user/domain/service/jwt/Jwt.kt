package com.jkojote.trex.user.domain.service.jwt

import java.time.ZonedDateTime

data class Jwt(
  val username: String,
  val expirationTime: ZonedDateTime
)