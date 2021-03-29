package com.jkojote.trex.template

import com.jkojote.trex.user.domain.model.VerificationToken

interface EmailVerificationTemplate : Template<EmailVerificationTemplate.Model> {

  data class Model(
    val verificationToken: VerificationToken
  )

}