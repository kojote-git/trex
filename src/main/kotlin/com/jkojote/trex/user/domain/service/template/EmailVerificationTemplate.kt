package com.jkojote.trex.user.domain.service.template

import com.jkojote.trex.user.domain.model.VerificationToken

interface EmailVerificationTemplate : Template<EmailVerificationTemplate.Model> {

  data class Model(
    val verificationToken: VerificationToken
  )

}