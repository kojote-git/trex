package com.jkojote.trex.history.domain.service.mongodb.converters

import com.jkojote.trex.history.domain.model.PlaceId
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class StringToPlaceIdConverter : Converter<String, PlaceId> {
  override fun convert(source: String): PlaceId {
    return PlaceId(source)
  }
}