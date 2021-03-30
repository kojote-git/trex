package com.jkojote.trex.place.domain.service.place.mongodb.converters

import com.jkojote.trex.place.domain.model.Location
import org.bson.BsonArray
import org.bson.BsonDocument
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class BsonDocumentToLocationConverter : Converter<BsonDocument, Location> {
  override fun convert(source: BsonDocument) : Location {
    val coordinates = source["coordinates"]

    if (coordinates == null) {
      throw IllegalArgumentException("No coordinates are present")
    }

    if (!coordinates.isArray) {
      throw IllegalArgumentException("Coordinates must be an array")
    }

    return convert(coordinates.asArray())
  }

  private fun convert(coordinates: BsonArray) : Location {
    if (coordinates.size < 2) {
      throw IllegalArgumentException("Coordinates must have latitude and longitude")
    }

    val lng = coordinates[0]
    val lat = coordinates[1]

    if (!lng.isDouble || !lat.isDouble) {
      throw IllegalArgumentException("Coordinates must be double values")
    }

    return Location(
      lat = lat.asDouble().value,
      lng = lng.asDouble().value
    )
  }
}