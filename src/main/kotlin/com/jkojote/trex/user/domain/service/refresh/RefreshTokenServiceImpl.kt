package com.jkojote.trex.user.domain.service.refresh

import com.jkojote.trex.user.domain.model.RefreshToken
import com.jkojote.trex.user.domain.model.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import java.util.*

@Service
@Transactional
class RefreshTokenServiceImpl(
  private val refreshTokenRepository: RefreshTokenRepository,
  @Value("\${jwt.refresh-token-lifetime-in-minutes:120}")
  private val refreshTokenLifeTimeInMinutes: Int
) : RefreshTokenService {

  override fun refreshToken(id: String): RefreshToken {
    val maybeRefreshToken = refreshTokenRepository.findById(id)
    if (maybeRefreshToken.isEmpty) {
      throw RefreshFailedException("Unknown token")
    }
    return refreshToken(maybeRefreshToken.get())
  }

  private fun refreshToken(refreshToken: RefreshToken) : RefreshToken {
    if (refreshToken.isExpired()) {
      throw RefreshFailedException("token is invalid")
    }
    deleteToken(refreshToken)
    return createNewToken(refreshToken.user)
  }

  override fun createTokenForUser(user: User) : RefreshToken {
    val existingToken = refreshTokenRepository.findByUser(user)
    if (existingToken.isPresent) {
      deleteToken(existingToken.get())
      return createNewToken(user)
    }
    return createNewToken(user)
  }

  private fun deleteToken(refreshToken: RefreshToken) {
    refreshTokenRepository.delete(refreshToken)
  }

  private fun createNewToken(user: User): RefreshToken {
    val token = RefreshToken(
      id = UUID.randomUUID().toString(),
      expirationDate = ZonedDateTime.now().plusMinutes(refreshTokenLifeTimeInMinutes.toLong()),
      user = user
    )
    return refreshTokenRepository.save(token)
  }
}