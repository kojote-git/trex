package com.jkojote.trex.user.domain.service.authentication

data class RefreshResult(
    val accessToken: String,
    val refreshToken: String
  )