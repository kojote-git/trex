package com.jkojote.trex.user.api.model.request

data class AuthenticateRequestDto(
  val email: String,
  val password: String
)