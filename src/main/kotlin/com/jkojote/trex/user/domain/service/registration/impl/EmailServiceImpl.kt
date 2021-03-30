package com.jkojote.trex.user.domain.service.registration.impl

import com.jkojote.trex.user.domain.model.VerificationToken
import com.jkojote.trex.user.domain.service.registration.EmailService
import com.jkojote.trex.user.domain.service.registration.template.EmailVerificationTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(
  private val emailVerificationTemplate: EmailVerificationTemplate,
  private val mailSender: JavaMailSender
) : EmailService {

  override fun sendVerificationEmail(verificationToken: VerificationToken) {
    val message = mailSender.createMimeMessage()

    with(message) {
      subject = "Email Verification"
      setRecipients(javax.mail.Message.RecipientType.TO, verificationToken.user.email)
      setContent(renderVerificationEmail(verificationToken), "text/html")
    }

    mailSender.send(message)
  }

  private fun renderVerificationEmail(verificationToken: VerificationToken) : String {
    val model = EmailVerificationTemplate.Model(verificationToken)
    return emailVerificationTemplate.render(model)
  }
}