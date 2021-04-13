package com.jkojote.trex.user.domain.service.authentication

data class AuthenticationResult(
  val accessToken: String,
  val refreshToken: String
)