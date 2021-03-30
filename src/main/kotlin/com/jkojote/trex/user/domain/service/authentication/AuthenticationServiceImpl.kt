package com.jkojote.trex.user.domain.service.authentication

import com.jkojote.trex.user.domain.model.User
import com.jkojote.trex.user.domain.service.jwt.JwtService
import com.jkojote.trex.user.domain.service.refresh.RefreshTokenService
import com.jkojote.trex.user.domain.service.user.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthenticationServiceImpl(
  private val refreshTokenService: RefreshTokenService,
  private val jwtService: JwtService,
  private val userService: UserService,
  private val passwordEncoder: PasswordEncoder
) : AuthenticationService {

  override fun authenticate(authenticationInput: AuthenticationService.AuthenticationInput) : AuthenticationResult {
    val user = getUser(authenticationInput.email)

    checkVerified(user)
    checkPassword(user, authenticationInput.password)

    val accessToken = jwtService.createToken(user.email)
    val refreshToken = refreshTokenService.createTokenForUser(user)
    return AuthenticationResult(
      accessToken = accessToken,
      refreshToken = refreshToken.id
    )
  }

  override fun refresh(refreshToken: String) : RefreshResult {
    val newRefreshToken = refreshTokenService.refreshToken(refreshToken)
    val newAccessToken = jwtService.createToken(newRefreshToken.user.email)

    return RefreshResult(
      accessToken = newAccessToken,
      refreshToken = newRefreshToken.id
    )
  }

  private fun getUser(email: String) : User {
    return userService.finUserByEmail(email).orElseThrow { AuthenticationFailedException("Unknown user") }
  }

  private fun checkPassword(user: User, password: String) {
    if (passwordEncoder.encode(password) != user.password) {
      throw AuthenticationFailedException("Passwords don't match")
    }
  }

  private fun checkVerified(user: User) {
    if (!user.verified) {
      throw AuthenticationFailedException("User is not verified")
    }
  }
}