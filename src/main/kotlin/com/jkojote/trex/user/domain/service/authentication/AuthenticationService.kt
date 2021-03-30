package com.jkojote.trex.user.domain.service.authentication

import com.jkojote.trex.user.domain.service.refresh.RefreshFailedException

interface AuthenticationService {

  @Throws(AuthenticationFailedException::class)
  fun authenticate(authenticationInput: AuthenticationInput) : AuthenticationResult

  @Throws(RefreshFailedException::class)
  fun refresh(refreshToken: String) : RefreshResult

  data class AuthenticationInput(
    val email: String,
    val password: String
  )

}