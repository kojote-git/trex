package com.jkojote.trex.place.api.service

import com.jkojote.trex.place.api.model.request.CreatePlaceRequestDto
import com.jkojote.trex.place.api.model.request.FindNearestPlacesRequestDto
import com.jkojote.trex.place.api.model.response.DetailedPlaceDto
import com.jkojote.trex.place.api.model.response.PlaceDto
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

  fun createPlace(requestDto: CreatePlaceRequestDto) : PlaceDto {
    val input = CreatePlaceInput(
      name = requestDto.name,
      category = requestDto.category,
      description = requestDto.description,
      location = requestDto.location,
      region = requestDto.region
    )
    val place = placeService.createPlace(input)
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

  fun searchNearestPlaces(requestDto: FindNearestPlacesRequestDto) : List<PlaceDto> {
    return placeService
      .findNearest(
        location = requestDto.location,
        distance = Distance(requestDto.distanceInMeters, Distance.Unit.METER)
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