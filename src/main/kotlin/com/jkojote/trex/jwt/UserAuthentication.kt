package com.jkojote.trex.jwt

import com.jkojote.trex.user.domain.model.User
import org.springframework.security.authentication.AbstractAuthenticationToken

class UserAuthentication(private val user: User) : AbstractAuthenticationToken(listOf(user.role)) {

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
