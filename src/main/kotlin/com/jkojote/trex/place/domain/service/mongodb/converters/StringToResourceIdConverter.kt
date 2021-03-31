package com.jkojote.trex.place.domain.service.mongodb.converters

import com.jkojote.trex.place.domain.model.ResourceId
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class StringToResourceIdConverter : Converter<String, ResourceId> {
  override fun convert(source: String): ResourceId {
    return ResourceId(source)
  }
}