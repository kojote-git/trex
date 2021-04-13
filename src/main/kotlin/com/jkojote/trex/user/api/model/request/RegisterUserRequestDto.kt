package com.jkojote.trex.user.api.model.request

import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

data class RegisterUserRequestDto(

  @NotEmpty
  @Email
  val email: String,

  @NotEmpty
  @Min(8)
  val password: String,
)