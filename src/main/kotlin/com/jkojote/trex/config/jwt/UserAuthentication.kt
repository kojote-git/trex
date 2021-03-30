package com.jkojote.trex.config.jwt

import com.jkojote.trex.user.domain.model.User
import org.springframework.security.authentication.AbstractAuthenticationToken

class UserAuthentication(private val user: User) : AbstractAuthenticationToken(emptyList()) {

  init {
    isAuthenticated = true
  }

  override fun getCredentials() : Any? {
    return null
  }

  override fun getPrincipal() : User {
    return user
  }
}
