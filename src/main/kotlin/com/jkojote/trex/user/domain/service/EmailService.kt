package com.jkojote.trex.user.domain.service

import com.jkojote.trex.user.domain.model.VerificationToken

interface EmailService {

  fun sendVerificationEmail(verificationToken: VerificationToken)

}