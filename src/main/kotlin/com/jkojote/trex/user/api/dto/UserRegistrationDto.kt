package com.jkojote.trex.user.api.dto

import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

data class UserRegistrationDto(

  @NotEmpty
  @Email
  val email: String,

  @NotEmpty
  @Min(8)
  val password: String,
)