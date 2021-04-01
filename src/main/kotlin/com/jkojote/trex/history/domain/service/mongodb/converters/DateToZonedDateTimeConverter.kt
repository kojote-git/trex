package com.jkojote.trex.history.domain.service.mongodb.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

@ReadingConverter
class DateToZonedDateTimeConverter : Converter<Date, ZonedDateTime> {
  override fun convert(source: Date): ZonedDateTime {
    return source.toInstant().atZone(ZoneOffset.UTC)
  }
}