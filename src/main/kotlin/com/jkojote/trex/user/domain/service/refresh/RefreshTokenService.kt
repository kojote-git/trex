package com.jkojote.trex.user.domain.service.refresh

import com.jkojote.trex.user.domain.model.RefreshToken
import com.jkojote.trex.user.domain.model.User

interface RefreshTokenService {

  @Throws(RefreshFailedException::class)
  fun refreshToken(id: String) : RefreshToken

  fun createTokenForUser(user: User) : RefreshToken

}