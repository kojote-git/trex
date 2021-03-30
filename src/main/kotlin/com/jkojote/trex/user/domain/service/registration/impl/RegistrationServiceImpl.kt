package com.jkojote.trex.user.domain.service.registration.impl

import com.jkojote.trex.user.domain.model.VerificationToken
import com.jkojote.trex.user.domain.model.User
import com.jkojote.trex.user.domain.service.registration.EmailService
import com.jkojote.trex.user.domain.service.registration.RegistrationService
import com.jkojote.trex.user.domain.service.registration.VerificationTokenService
import com.jkojote.trex.user.domain.service.user.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
@Transactional
class RegistrationServiceImpl(
  private val userService: UserService,
  private val verificationTokenService: VerificationTokenService,
  private val emailService: EmailService,
  private val passwordEncoder: PasswordEncoder
) : RegistrationService {

  override fun registerUser(input: RegistrationService.RegisterUserInput) : User {
    val user = createUser(input)
    val verificationToken = createVerificationToken(user)
    emailService.sendVerificationEmail(verificationToken)
    return user
  }

  override fun verifyUser(verificationTokenKey: String) : Boolean {
    val verificationToken = verificationTokenService.findTokenByKey(verificationTokenKey)
    if (verificationToken.isPresent) {
      return verifyUser(verificationToken.get())
    }

    return false
  }

  private fun createUser(registerUser: RegistrationService.RegisterUserInput) : User {
    val userCreationInput = UserService.CreateUserInput(
      email = registerUser.email,
      password = passwordEncoder.encode(registerUser.rawPassword)
    )
    return userService.createUser(userCreationInput)
  }

  private fun createVerificationToken(user: User) : VerificationToken {
    return verificationTokenService.createToken(user, Duration.ofHours(1))
  }

  private fun verifyUser(verificationToken: VerificationToken) : Boolean {
    if (verificationToken.isExpired()) {
      return false
    }

    verificationTokenService.deleteToken(verificationToken)
    userService.verifyUser(verificationToken.user)
    return true
  }
}