package com.jkojote.trex.history.domain.service.mongodb.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import java.time.ZonedDateTime
import java.util.*

@WritingConverter
class ZonedDateTimeToDateConverter : Converter<ZonedDateTime, Date> {
  override fun convert(source: ZonedDateTime): Date {
    return Date.from(source.toInstant())
  }

}