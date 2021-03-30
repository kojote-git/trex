package com.jkojote.trex.place.domain.service.place.mongodb

import com.jkojote.trex.place.domain.model.Distance
import com.jkojote.trex.place.domain.model.Image
import com.jkojote.trex.place.domain.model.Location
import com.jkojote.trex.place.domain.model.Place
import com.jkojote.trex.place.domain.service.content.ContentService
import com.jkojote.trex.place.domain.service.place.CreatePlaceInput
import com.jkojote.trex.place.domain.service.place.PlaceService
import org.bson.types.ObjectId
import org.springframework.core.io.Resource
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.Update.update
import org.springframework.data.mongodb.core.query.UpdateDefinition
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import java.util.*

@Service
class MongoPlaceService(
  private val contentService: ContentService,
  private val mongoOperations: MongoOperations
) : PlaceService {

  companion object {
    const val ID = "id"
    const val THUMBNAIL = "thumbnail"
    const val LOCATION = "location"
    const val PHOTOS = "photos"
  }

  override fun createPlace(input: CreatePlaceInput) : Place {
    val place = Place(
      id = ObjectId().toHexString(),
      name = input.name,
      category = input.category,
      description = input.description,
      location = input.location,
      region = input.region,
      thumbnail = null,
      photos = emptyList()
    )
    mongoOperations.save(place)
    return place
  }

  override fun setThumbnail(place: Place, photo: Resource) : Image {
    val image = savePhoto(photo)

    mongoOperations.updateFirst(
      query(where(ID).isEqualTo(place.id)),
      update(THUMBNAIL, image),
      Place::class.java
    )

    return image
  }

  override fun addPhoto(place: Place, photo: Resource): Image {
    val image = savePhoto(photo)

    mongoOperations.updateFirst(
      query(where(ID).isEqualTo(place.id)),
      push(PHOTOS, image),
      Place::class.java
    )

    return image
  }

  override fun removePhoto(place: Place, photo: Image) {
    mongoOperations.updateFirst(
      query(where(ID).isEqualTo(place.id)),
      pull(PHOTOS, photo),
      Place::class.java
    )
  }

  override fun findPlaceById(placeId: String): Optional<Place> {
    val result = mongoOperations.findOne(
      query(where(ID).isEqualTo(placeId)),
      Place::class.java
    )
    return Optional.of(result)
  }

  override fun findNearest(location: Location, distance: Distance): List<Place> {
    return mongoOperations.find(
      query(
        locationNear(
          location = location,
          maxDistance = distance
        )
      ),
      Place::class.java
    )
  }

  private fun savePhoto(photo: Resource) : Image {
    val contentId = photo.inputStream.use {
      contentService.saveContent(it)
    }
    return Image(contentId)
  }

  private fun push(key: String, value: Any?) : UpdateDefinition {
    return Update().push(key, value)
  }

  private fun pull(key: String, value: Any?) : UpdateDefinition {
    return Update().pull(key, value)
  }

  private fun locationNear(location: Location, maxDistance: Distance) : NearCriteriaDefinition {
    return NearCriteriaDefinition(
      key = LOCATION,
      location = location,
      maxDistance = maxDistance
    )
  }

}