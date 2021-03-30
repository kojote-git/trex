package com.jkojote.trex.user.domain.service.user

import com.jkojote.trex.user.domain.model.User
import com.jkojote.trex.user.domain.service.user.exception.UserAlreadyExistsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserServiceImpl(
  private val userRepository: UserRepository,
) : UserService {

  override fun createUser(user: UserService.CreateUserInput) : User {
    checkUserDoesntExist(user.email)

    val savedUser = User(
      email = user.email,
      password = user.password
    )
    return userRepository.save(savedUser)
  }

  override fun verifyUser(user: User) {
    userRepository.setVerified(user.id!!)
  }

  override fun finUserByEmail(email: String): Optional<User> {
    return userRepository.findByEmail(email)
  }

  private fun checkUserDoesntExist(email: String) {
    if (userRepository.existsByEmail(email)) {
      throw UserAlreadyExistsException("User with this $email email already exists")
    }
  }

}