package com.jkojote.trex.place.domain.service.mongodb.converters

import com.jkojote.trex.place.domain.model.ResourceId
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class ResourceIdToStringConverter : Converter<ResourceId, String> {

  override fun convert(source: ResourceId): String {
    return source.id
  }
}