package com.jkojote.trex.config

import com.jkojote.trex.history.domain.service.mongodb.converters.*
import com.jkojote.trex.place.domain.service.mongodb.converters.DocumentToLocationConverter
import com.jkojote.trex.place.domain.service.mongodb.converters.LocationToBsonDocumentConverter
import com.jkojote.trex.place.domain.service.mongodb.converters.ResourceIdToStringConverter
import com.jkojote.trex.place.domain.service.mongodb.converters.StringToResourceIdConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoConversionsConfig {

  @Bean
  fun mongoCustomConversions() : MongoCustomConversions {
    return MongoCustomConversions(listOf(
      ResourceIdToStringConverter(),
      StringToResourceIdConverter(),

      LocationToBsonDocumentConverter(),
      DocumentToLocationConverter(),

      PlaceIdToStringConverter(),
      StringToPlaceIdConverter(),

      UserIdToLongConverter(),
      LongToUserIdConverter(),

      ZonedDateTimeToDateConverter(),
      DateToZonedDateTimeConverter()
    ))
  }

}