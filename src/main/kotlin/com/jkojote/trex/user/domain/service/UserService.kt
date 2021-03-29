package com.jkojote.trex.user.domain.service

import com.jkojote.trex.user.domain.model.User
import com.jkojote.trex.user.domain.service.exception.UserAlreadyExistsException
import java.util.*

interface UserService {

  @Throws(UserAlreadyExistsException::class)
  fun createUser(user: CreateUserInput) : User

  fun verifyUser(user: User)

  fun finUserByEmail(email: String) : Optional<User>

  class CreateUserInput(
    val email: String,
    val password: String
  )

}