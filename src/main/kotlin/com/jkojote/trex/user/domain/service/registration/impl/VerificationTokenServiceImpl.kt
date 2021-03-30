package com.jkojote.trex.user.domain.service.registration.impl

import com.jkojote.trex.user.domain.model.VerificationToken
import com.jkojote.trex.user.domain.model.User
import com.jkojote.trex.user.domain.service.registration.VerificationTokenRepository
import com.jkojote.trex.user.domain.service.registration.VerificationTokenService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.ZonedDateTime
import java.util.*

@Service
@Transactional
class VerificationTokenServiceImpl(
  private val verificationTokenRepository: VerificationTokenRepository
) : VerificationTokenService {

  override fun createToken(user: User, duration: Duration) : VerificationToken {
    return verificationTokenRepository
      .findByUser(user)
      .map { recreateVerificationToken(it, duration) }
      .orElseGet { createNewVerificationToken(user, duration) }
  }

  private fun recreateVerificationToken(verificationToken: VerificationToken, duration: Duration) : VerificationToken {
    deleteToken(verificationToken)
    return createNewVerificationToken(verificationToken.user, duration)
  }

  private fun createNewVerificationToken(user: User, duration: Duration) : VerificationToken {
    val verificationToken = VerificationToken(
      user = user,
      expirationTime = ZonedDateTime.now() + duration,
      key = UUID.randomUUID().toString()
    )
    return verificationTokenRepository.save(verificationToken)
  }

  override fun deleteToken(verificationToken: VerificationToken) {
    verificationTokenRepository.delete(verificationToken)
  }

  override fun findTokenByKey(key: String): Optional<VerificationToken> {
    return verificationTokenRepository.findByKey(key)
  }
}