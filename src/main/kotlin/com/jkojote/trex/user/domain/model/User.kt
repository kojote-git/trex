package com.jkojote.trex.user.domain.model

import javax.persistence.*

@Entity
class User(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(nullable = false, unique = true)
  val email: String,

  @Column(nullable = false)
  val password: String,

  @Column(nullable = false)
  val verified: Boolean = false,

  @Enumerated(EnumType.STRING)
  val role: Role = Role.USER
)