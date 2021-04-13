package com.jkojote.trex.user.api.model.response

import com.jkojote.trex.user.domain.service.authentication.AuthenticationResult

class AuthenticationResultDto(
  val accessToken: String,
  val refreshToken: String
) {
  companion object {
    fun fromAuthenticationResult(authenticationResult: AuthenticationResult) : AuthenticationResultDto {
      return AuthenticationResultDto(
        accessToken = authenticationResult.accessToken,
        refreshToken = authenticationResult.refreshToken
      )
    }
  }
}