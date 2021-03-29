package com.jkojote.trex.user.domain.model

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
class VerificationToken(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(nullable = false, unique = true)
  val key: String,

  @Column(nullable = false)
  val expirationTime: ZonedDateTime,

  @OneToOne
  val user: User
) {

  fun isExpired() : Boolean {
    return ZonedDateTime.now() < expirationTime
  }
}