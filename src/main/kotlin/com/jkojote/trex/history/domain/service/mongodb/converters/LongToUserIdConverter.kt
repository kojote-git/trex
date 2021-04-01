package com.jkojote.trex.history.domain.service.mongodb.converters

import com.jkojote.trex.history.domain.model.UserId
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class LongToUserIdConverter : Converter<Long, UserId> {
  override fun convert(source: Long) : UserId {
    return UserId(source)
  }
}