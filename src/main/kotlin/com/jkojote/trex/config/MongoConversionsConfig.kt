package com.jkojote.trex.config

import com.jkojote.trex.place.domain.service.place.mongodb.converters.BsonDocumentToLocationConverter
import com.jkojote.trex.place.domain.service.place.mongodb.converters.LocationToBsonDocumentConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoConversionsConfig {

  @Bean
  fun mongoCustomConversions() : MongoCustomConversions {
    return MongoCustomConversions(listOf(
      LocationToBsonDocumentConverter(),
      BsonDocumentToLocationConverter()
    ))
  }

}