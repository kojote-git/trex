package com.jkojote.trex.place.api.service

import com.jkojote.trex.place.api.dto.DetailedPlaceDto
import com.jkojote.trex.place.api.dto.PlaceDto
import com.jkojote.trex.place.api.dto.NearLocationQueryDto
import com.jkojote.trex.place.api.service.exception.PlaceNotFoundException
import com.jkojote.trex.place.domain.model.Distance
import com.jkojote.trex.place.domain.model.Place
import com.jkojote.trex.place.domain.model.ResourceId
import com.jkojote.trex.place.domain.service.CreatePlaceInput
import com.jkojote.trex.place.domain.service.PlaceService
import com.jkojote.trex.resource.domain.model.Resource
import com.jkojote.trex.resource.domain.service.CreateResourceInput
import com.jkojote.trex.resource.domain.service.ResourceService
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.*

@Service
class PlaceServiceFacade(
  private val placeService: PlaceService,
  private val resourceService: ResourceService
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

  fun setThumbnail(placeId: String, content: InputStream, contentType: String) {
    val place = getPlace(placeId)
    val resource = saveContent(content, contentType)
    placeService.setThumbnail(place, ResourceId(resource.id))
  }

  fun addPhoto(placeId: String, content: InputStream, contentType: String) {
    val place = getPlace(placeId)
    val resource = saveContent(content, contentType)
    placeService.addPhoto(place, ResourceId(resource.id))
  }

  fun removePhoto(placeId: String, resourceId: String) {
    val place = getPlace(placeId)
    placeService.removePhoto(place, ResourceId(resourceId))
  }

  fun searchNearestPlaces(nearLocationQueryDto: NearLocationQueryDto) : List<PlaceDto> {
    return placeService
      .findNearest(
        location = nearLocationQueryDto.location,
        distance = Distance(nearLocationQueryDto.distanceInMeters, Distance.Unit.METER)
      )
      .map { PlaceDto.fromPlace(it) }
  }

  private fun getPlace(placeId: String) : Place {
    return placeService
      .findPlaceById(placeId)
      .orElseThrow { PlaceNotFoundException("Unknown place id $placeId") }
  }

  private fun saveContent(content: InputStream, contentType: String) : Resource {
    val input = CreateResourceInput(
      contentType = contentType
    )
    val resource = resourceService.createResource(input)
    resourceService.saveContent(resource, content)
    return resource
  }
}