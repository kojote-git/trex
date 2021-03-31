package com.jkojote.trex.place.domain.service.mongodb.converters

import com.jkojote.trex.place.domain.model.Location
import org.bson.BsonArray
import org.bson.BsonDocument
import org.bson.BsonDouble
import org.bson.BsonString
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class LocationToBsonDocumentConverter : Converter<Location, BsonDocument> {

  override fun convert(source: Location): BsonDocument {
    return BsonDocument().apply {
      set("type", BsonString("Point"))
      set("coordinates", BsonArray().apply {
        add(BsonDouble(source.lng))
        add(BsonDouble(source.lat))
      })
    }
  }
}