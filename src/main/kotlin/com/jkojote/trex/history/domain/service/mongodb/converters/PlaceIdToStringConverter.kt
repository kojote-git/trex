package com.jkojote.trex.history.domain.service.mongodb.converters

import com.jkojote.trex.history.domain.model.PlaceId
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class PlaceIdToStringConverter : Converter<PlaceId, String> {
  override fun convert(source: PlaceId): String {
    return source.id
  }
}