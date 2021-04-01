package com.jkojote.trex.config

import springfox.documentation.builders.PathSelectors.ant

import springfox.documentation.spi.DocumentationType

import springfox.documentation.spring.web.plugins.Docket

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {
  @Bean
  fun apiDocket(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
      .groupName("API")
      .select()
      .paths(ant("/api/**"))
      .build()
  }
}