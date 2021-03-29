package com.jkojote.trex.user.service

import com.jkojote.trex.user.domain.model.User
import com.jkojote.trex.user.domain.model.VerificationToken
import com.jkojote.trex.user.domain.service.EmailService
import com.jkojote.trex.user.domain.service.RegistrationService
import com.jkojote.trex.user.domain.service.UserService
import com.jkojote.trex.user.domain.service.VerificationTokenService
import com.jkojote.trex.user.domain.service.impl.RegistrationServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import java.time.Duration
import java.time.ZonedDateTime
import java.util.*

class RegistrationServiceTest {
  private lateinit var registrationService: RegistrationService
  private lateinit var userService: UserService
  private lateinit var emailService: EmailService
  private lateinit var verificationTokenService: VerificationTokenService

  @BeforeEach
  fun beforeEach() {
    userService = mock(UserService::class.java)
    registrationService = mock(RegistrationService::class.java)
    emailService = mock(EmailService::class.java)
    verificationTokenService = mock(VerificationTokenService::class.java)

    registrationService = RegistrationServiceImpl(
      userService = userService,
      emailService = emailService,
      verificationTokenService = verificationTokenService,
      passwordEncoder = NoOpPasswordEncoder.getInstance()
    )
  }

  @Test
  fun registerUser_registerNewUserThatDoesntExists() {
    val input = RegistrationService.RegisterUserInput(
      email = "example@gmail.com",
      rawPassword = "example"
    )
    val user = User(id = 1, email = "example@gmail.com", password = "example")
    val verificationToken = VerificationToken(
      key = UUID.randomUUID().toString(),
      expirationTime = ZonedDateTime.now() + Duration.ofHours(1),
      user = user
    )
    doReturn(user).`when`(userService.finUserByEmail(any()))
    doReturn(verificationToken).`when`(verificationTokenService.createToken(user, any()))

    registrationService.registerUser(input)

    verify(userService).createUser(any())
    verify(verificationTokenService).createToken(user, any())
    verify(emailService).sendVerificationEmail(verificationToken)
  }

  private fun <T> any() : T {
    Mockito.any<T>()
    return null as T
  }

}