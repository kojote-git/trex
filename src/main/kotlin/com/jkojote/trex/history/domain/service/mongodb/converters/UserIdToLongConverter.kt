package com.jkojote.trex.history.domain.service.mongodb.converters

import com.jkojote.trex.history.domain.model.UserId
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class UserIdToLongConverter : Converter<UserId, Long> {
  override fun convert(source: UserId): Long {
    return source.id
  }
}