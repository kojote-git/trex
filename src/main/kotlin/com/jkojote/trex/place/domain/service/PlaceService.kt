package com.jkojote.trex.place.domain.service

import com.jkojote.trex.place.domain.model.Distance
import com.jkojote.trex.place.domain.model.Location
import com.jkojote.trex.place.domain.model.Place
import com.jkojote.trex.place.domain.model.ResourceId
import java.util.*

interface PlaceService {

  fun createPlace(input: CreatePlaceInput) : Place

  fun findPlaceById(placeId: String) : Optional<Place>

  fun setThumbnail(place: Place, resourceId: ResourceId)

  fun addPhoto(place: Place, resourceId: ResourceId)

  fun removePhoto(place: Place, resourceId: ResourceId)

  fun findNearest(location: Location, distance: Distance) : List<Place>

}