package com.jkojote.trex.history.domain.service.mongodb

import com.jkojote.trex.history.domain.model.History
import com.jkojote.trex.history.domain.model.PlaceId
import com.jkojote.trex.history.domain.model.SavedPlace
import com.jkojote.trex.history.domain.model.UserId
import com.jkojote.trex.history.domain.service.HistoryService
import com.jkojote.trex.util.has
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.UpdateDefinition
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*
import kotlin.collections.ArrayList

@Service
class MongoHistoryService(private val mongoOperations: MongoOperations) : HistoryService {
  companion object {
    const val USER_ID = "userId"
    const val PLACES = "places"
  }

  override fun createHistory(userId: UserId) : History {
    val history = History(
      userId = userId,
      places = emptyList()
    )
    mongoOperations.save(history)
    return history
  }

  override fun findHistory(userId: UserId) : Optional<History> {
    val history = mongoOperations.findOne(
      query(where(USER_ID).isEqualTo(userId)),
      History::class.java
    )
    return Optional.ofNullable(history)
  }

  override fun addSavedPlace(history: History, placeId: PlaceId) : History {
    if (!history.places.has { it.placeId == placeId }) {
      val savedPlace = SavedPlace(
        placeId = placeId,
        date = ZonedDateTime.now()
      )
      mongoOperations.updateFirst(
        query(where(USER_ID).isEqualTo(history.userId)),
        push(PLACES, savedPlace),
        History::class.java
      )
      return history.copy(
        places = ArrayList(history.places).apply {
          add(savedPlace)
        }
      )
    }

    return history
  }

  override fun removeSavedPlace(history: History, placeId: PlaceId) : History {
    if (history.places.has { it.placeId == placeId }) {
      mongoOperations.updateFirst(
        query(where(USER_ID).isEqualTo(history.userId)),
        pull(PLACES, mapOf("placeId" to placeId)),
        History::class.java
      )
    }

    return history
  }

  private fun push(key: String, value: Any?) : UpdateDefinition {
    return Update().push(key, value)
  }

  private fun pull(key: String, value: Any?) : UpdateDefinition {
    return Update().pull(key, value)
  }

}