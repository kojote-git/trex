package com.jkojote.trex.place.api

import com.jkojote.trex.place.api.model.request.CreatePlaceRequestDto
import com.jkojote.trex.place.api.model.request.FindNearestPlacesRequestDto
import com.jkojote.trex.place.api.model.response.DetailedPlaceDto
import com.jkojote.trex.place.api.model.response.PlaceDto
import com.jkojote.trex.place.api.service.PlaceServiceFacade
import com.jkojote.trex.place.api.service.exception.PhotoNotFoundException
import com.jkojote.trex.place.api.service.exception.PlaceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/api/place"], produces = ["application/json"])
class PlaceApi(
  private val placeServiceFacade: PlaceServiceFacade
) {

  @PostMapping
  fun createPlace(@RequestBody @Valid requestDto: CreatePlaceRequestDto) : ResponseEntity<PlaceDto> {
    val place = placeServiceFacade.createPlace(requestDto)
    return ResponseEntity(place, HttpStatus.OK)
  }

  @GetMapping("{id}")
  fun findPlaceById(@PathVariable id: String) : ResponseEntity<DetailedPlaceDto> {
    val place = placeServiceFacade.findPlaceById(id)
    if (place.isEmpty) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    }
    return ResponseEntity(place.get(), HttpStatus.OK)
  }

  @PutMapping("{id}/thumbnail")
  fun setThumbnail(@PathVariable("id") id: String,
                   @RequestParam("file") file: MultipartFile) : ResponseEntity<Any> {

    try {
      placeServiceFacade.setThumbnail(
        placeId = id,
        content = file.inputStream,
        contentType = file.contentType ?: "image/jpeg"
      )
      return ResponseEntity(HttpStatus.OK)
    } catch (e: PlaceNotFoundException) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }

  @PostMapping("{id}/photo")
  fun addPhoto(@PathVariable("id") id: String,
               @RequestParam("file") file: MultipartFile) : ResponseEntity<Any> {

    try {
      placeServiceFacade.addPhoto(
        placeId = id,
        content = file.inputStream,
        contentType = file.contentType ?: "image/jpeg"
      )
      return ResponseEntity(HttpStatus.OK)
    } catch (e: PlaceNotFoundException) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }

  @DeleteMapping("{id}/photo/{resourceId}")
  fun removePhoto(@PathVariable("id") id: String,
                  @PathVariable("resourceId") resourceId: String) : ResponseEntity<Any> {

    try {
      placeServiceFacade.removePhoto(id, resourceId)
      return ResponseEntity(HttpStatus.OK)
    } catch (e: PlaceNotFoundException) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    } catch (e: PhotoNotFoundException) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }

  @PostMapping("search/nearest")
  fun findNearestPlaces(@RequestBody requestDto: FindNearestPlacesRequestDto) : ResponseEntity<List<PlaceDto>> {
    val places = placeServiceFacade.searchNearestPlaces(requestDto)
    return ResponseEntity(places, HttpStatus.OK)
  }
}