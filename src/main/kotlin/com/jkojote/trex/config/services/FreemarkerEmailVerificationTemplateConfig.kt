package com.jkojote.trex.config.services

import com.jkojote.trex.user.domain.service.registration.template.EmailVerificationTemplate
import com.jkojote.trex.user.domain.service.registration.template.freemarker.FreemarkerEmailVerificationTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FreemarkerEmailVerificationTemplateConfig {

  @Value("\${verification-template.freemarker.verification-endpoint}")
  private lateinit var verificationEndpoint: String

  @Bean
  fun freeMarkerVerificationTemplate() : EmailVerificationTemplate {
    return FreemarkerEmailVerificationTemplate(verificationEndpoint)
  }
}