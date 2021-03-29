package com.jkojote.trex.user.domain.repository

import com.jkojote.trex.user.domain.model.VerificationToken
import com.jkojote.trex.user.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {

  fun findByKey(key: String) : Optional<VerificationToken>

  fun findByUser(user: User) : Optional<VerificationToken>


}