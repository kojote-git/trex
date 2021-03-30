package com.jkojote.trex.user.domain.service.refresh

import com.jkojote.trex.user.domain.model.RefreshToken
import com.jkojote.trex.user.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {

  fun findByUser(user: User): Optional<RefreshToken>
}
