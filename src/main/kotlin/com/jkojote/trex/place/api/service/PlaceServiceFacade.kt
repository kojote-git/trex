package com.jkojote.trex.place.api.service

import com.jkojote.trex.place.api.dto.DetailedPlaceDto
import com.jkojote.trex.place.api.dto.ImageDto
import com.jkojote.trex.place.api.dto.PlaceDto
import com.jkojote.trex.place.api.dto.SearchNearestDto
import com.jkojote.trex.place.api.service.exception.PhotoNotFoundException
import com.jkojote.trex.place.api.service.exception.PlaceNotFoundException
import com.jkojote.trex.place.domain.model.Distance
import com.jkojote.trex.place.domain.model.Image
import com.jkojote.trex.place.domain.model.Place
import com.jkojote.trex.place.domain.service.place.CreatePlaceInput
import com.jkojote.trex.place.domain.service.place.PlaceService
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.*

@Service
class PlaceServiceFacade(
  private val placeService: PlaceService
) {

  fun createPlace(createPlaceInput: CreatePlaceInput) : PlaceDto {
    val place = placeService.createPlace(createPlaceInput)
    return PlaceDto.fromPlace(place)
  }

  fun findPlaceById(id: String) : Optional<DetailedPlaceDto> {
    return placeService
      .findPlaceById(id)
      .map { DetailedPlaceDto.fromPlace(it) }
  }

  fun setThumbnail(placeId: String, thumbnail: InputStream, contentType: String) {
    val place = getPlace(placeId)
    placeService.setThumbnail(place, thumbnail, contentType)
  }

  fun addPhoto(placeId: String, content: InputStream, contentType: String) : ImageDto {
    val place = getPlace(placeId)
    val image = placeService.addPhoto(place, content, contentType)
    return ImageDto.fromImage(image)
  }

  fun removePhoto(placeId: String, contentId: String) {
    val place = getPlace(placeId)
    val photo = getPhoto(place, contentId)
    placeService.removePhoto(place, photo)
  }

  fun searchNearestPlaces(searchNearestDto: SearchNearestDto) : List<PlaceDto> {
    return placeService
      .findNearest(
        location = searchNearestDto.location,
        distance = Distance(searchNearestDto.distanceInMeters, Distance.Unit.METER)
      )
      .map { PlaceDto.fromPlace(it) }
  }

  private fun getPlace(placeId: String) : Place {
    return placeService
      .findPlaceById(placeId)
      .orElseThrow { PlaceNotFoundException("Unknown place id $placeId") }
  }

  private fun getPhoto(place: Place, contentId: String) : Image {
    val photo = place.photos.find { it.contentId.value == contentId }
    return photo ?: throw PhotoNotFoundException("Unknown photo with id $contentId")
  }


}