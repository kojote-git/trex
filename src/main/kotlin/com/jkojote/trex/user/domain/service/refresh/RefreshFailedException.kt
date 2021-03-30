package com.jkojote.trex.user.domain.service.refresh

class RefreshFailedException : RuntimeException {

  constructor(message: String) : super(message)

  constructor(message: String, cause: Throwable) : super(message, cause)
}
