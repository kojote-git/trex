package com.jkojote.trex

import com.fasterxml.jackson.databind.ObjectMapper
import com.jkojote.trex.place.api.model.request.CreatePlaceRequestDto
import com.jkojote.trex.place.api.service.PlaceServiceFacade
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import java.io.File
import java.io.FileInputStream

@SpringBootApplication
class TrexBackend

fun main(args: Array<String>) {
  val ctx = runApplication<TrexBackend>(*args)
//  loadData(ctx)
}

fun loadData(ctx: ApplicationContext) {
  val objectMapper = ctx.getBean(ObjectMapper::class.java)
  val placeServiceFacade = ctx.getBean(PlaceServiceFacade::class.java)
  val dataDirectory = File("/home/isaac/Desktop/data/places")

  for (placeDirectory in dataDirectory.listFiles()) {
    val jsonFile = File(placeDirectory, "place.json")
    val photoFile = File(placeDirectory, "place.png")

    val request = objectMapper.readValue(jsonFile, CreatePlaceRequestDto::class.java)
    val place = placeServiceFacade.createPlace(request)
    FileInputStream(photoFile).use {
      placeServiceFacade.setThumbnail(place.id, it, "image/png")
    }
  }
}