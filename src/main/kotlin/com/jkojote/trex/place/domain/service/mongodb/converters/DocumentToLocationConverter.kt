package com.jkojote.trex.place.domain.service.mongodb.converters

import com.jkojote.trex.place.domain.model.Location
import org.bson.Document
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class DocumentToLocationConverter : Converter<Document, Location> {
  override fun convert(source: Document) : Location {
    val coordinates = source["coordinates"]

    if (coordinates == null) {
      throw IllegalArgumentException("No coordinates are present")
    }

    if (coordinates !is List<*>) {
      throw IllegalArgumentException("Coordinates must be an array")
    }

    return convert(coordinates)
  }

  private fun convert(coordinates: List<*>) : Location {
    if (coordinates.size < 2) {
      throw IllegalArgumentException("Coordinates must have latitude and longitude")
    }

    val lng = coordinates[0]
    val lat = coordinates[1]

    if (lng !is Double || lat !is Double) {
      throw IllegalArgumentException("Coordinates must be double values")
    }

    return Location(
      lat = lat,
      lng = lng
    )
  }
}