package com.jkojote.trex.user.domain.service.user

import com.jkojote.trex.user.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

  fun findByEmail(email: String) : Optional<User>

  fun existsByEmail(email: String) : Boolean

  @Modifying
  @Query("UPDATE User u SET u.verified = true WHERE u.id = :userId")
  fun setVerified(@Param("userId") userId: Long)

}