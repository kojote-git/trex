package com.jkojote.trex.user.domain.model

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table
class RefreshToken(

  @Id
  val id: String,

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "id")
  val user: User,

  val expirationDate: ZonedDateTime
) {

  fun isExpired() : Boolean {
    return ZonedDateTime.now() > expirationDate
  }
}
