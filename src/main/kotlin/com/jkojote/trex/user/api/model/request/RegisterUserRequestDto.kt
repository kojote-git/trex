package com.jkojote.trex.user.api.model.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class RegisterUserRequestDto(

  @field:NotEmpty
  @field:Email
  val email: String,

  @field:NotEmpty
  @field:Size(min = 8)
  val password: String,
)