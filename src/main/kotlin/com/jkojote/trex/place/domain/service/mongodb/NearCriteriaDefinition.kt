package com.jkojote.trex.place.domain.service.mongodb

import com.jkojote.trex.place.domain.model.Distance
import com.jkojote.trex.place.domain.model.Location
import org.bson.Document
import org.springframework.data.mongodb.core.query.CriteriaDefinition

class NearCriteriaDefinition(
  private val key: String,
  private val location: Location,
  private val minDistance: Distance? = null,
  private val maxDistance: Distance? = null
) : CriteriaDefinition {

  override fun getCriteriaObject() : Document {
    return Document().apply {
      set("\$near", Document().apply {
        set("\$geometry", Document().apply {
          set("type", "Point")
          set("coordinates", listOf(location.lng, location.lat))
        })

        if (minDistance != null) {
          set("\$minDistance", minDistance.value)
        }

        if (maxDistance != null) {
          set("\$maxDistance", maxDistance.value)
        }
      })
    }
  }

  override fun getKey(): String {
    return key
  }
}