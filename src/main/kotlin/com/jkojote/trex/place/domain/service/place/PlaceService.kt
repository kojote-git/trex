package com.jkojote.trex.place.domain.service.place

import com.jkojote.trex.place.domain.model.Distance
import com.jkojote.trex.place.domain.model.Image
import com.jkojote.trex.place.domain.model.Location
import com.jkojote.trex.place.domain.model.Place
import org.springframework.core.io.Resource
import java.util.*

interface PlaceService {

  fun createPlace(input: CreatePlaceInput) : Place

  fun setThumbnail(place: Place, photo: Resource) : Image

  fun addPhoto(place: Place, photo: Resource) : Image

  fun removePhoto(place: Place, photo: Image)

  fun findPlaceById(placeId: String) : Optional<Place>

  fun findNearest(location: Location, distance: Distance) : List<Place>

}