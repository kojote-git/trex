package com.jkojote.trex.user.domain.service.registration

import com.jkojote.trex.user.domain.model.VerificationToken
import com.jkojote.trex.user.domain.model.User
import java.time.Duration
import java.util.*

interface VerificationTokenService {

  fun createToken(user: User, duration: Duration) : VerificationToken

  fun deleteToken(verificationToken: VerificationToken)

  fun findTokenByKey(key: String) : Optional<VerificationToken>

}