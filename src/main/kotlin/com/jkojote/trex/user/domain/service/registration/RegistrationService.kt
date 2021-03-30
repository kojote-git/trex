package com.jkojote.trex.user.domain.service.registration

import com.jkojote.trex.user.domain.model.User
import com.jkojote.trex.user.domain.service.user.exception.UserAlreadyExistsException

interface RegistrationService {

  @Throws(UserAlreadyExistsException::class)
  fun registerUser(input: RegisterUserInput) : User

  fun verifyUser(verificationTokenKey: String) : Boolean

  data class RegisterUserInput(
    val email: String,
    val rawPassword: String
  )

}