package com.jkojote.trex.user.domain.service.authentication

class AuthenticationFailedException : RuntimeException {

  constructor(message: String) : super(message)

  constructor(message: String, cause: Throwable) : super(message, cause)
}
