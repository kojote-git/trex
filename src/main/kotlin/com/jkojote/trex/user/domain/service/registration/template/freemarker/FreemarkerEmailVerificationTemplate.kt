package com.jkojote.trex.user.domain.service.registration.template.freemarker

import com.jkojote.trex.user.domain.service.registration.template.EmailVerificationTemplate
import freemarker.template.Configuration
import org.springframework.stereotype.Component
import java.io.StringWriter

@Component
class FreemarkerEmailVerificationTemplate : EmailVerificationTemplate {
  private val freemarkerTemplate: freemarker.template.Template

  // FIXME this is here for demo purposes
  private val verificationEndpoint = "http://localhost:8080/api/user/registration/verification"

  init {
    val configuration = Configuration(Configuration.VERSION_2_3_30)
    configuration.setClassForTemplateLoading(this.javaClass, "/templates")
    freemarkerTemplate = configuration.getTemplate("email-template.ftl")
  }

  override fun render(model: EmailVerificationTemplate.Model) : String {
    val freemarkerModel = mapOf(
      "verificationUrl" to "${verificationEndpoint}?token=${model.verificationToken.key}"
    )
    val writer = StringWriter()
    freemarkerTemplate.process(freemarkerModel, writer)
    return writer.toString()
  }
}