package com.jkojote.trex.config

import org.springframework.context.annotation.Configuration
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Bean

@Configuration
class ObjectMapperConfig {

  @Bean
  @Primary
  fun objectMapper(): ObjectMapper? {
    return ObjectMapper()
      .registerModule(JavaTimeModule())
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
      .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
  }
}