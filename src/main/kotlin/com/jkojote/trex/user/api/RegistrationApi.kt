package com.jkojote.trex.user.api

import com.jkojote.trex.user.api.model.request.RegisterUserRequestDto
import com.jkojote.trex.user.domain.service.registration.RegistrationService
import com.jkojote.trex.user.domain.service.user.exception.UserAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/api/user/registration"], produces = ["application/json"])
class RegistrationApi(private val registrationService: RegistrationService) {

  @PostMapping
  fun registerUser(@RequestBody @Valid userRegistrationDto: RegisterUserRequestDto) : ResponseEntity<Unit> {
    try {
      registrationService.registerUser(
        RegistrationService.RegisterUserInput(
          email = userRegistrationDto.email,
          rawPassword = userRegistrationDto.password
      ))

      return ResponseEntity(HttpStatus.OK)
    } catch (e: UserAlreadyExistsException) {
      return ResponseEntity(HttpStatus.CONFLICT)
    }
  }

  @GetMapping("verification")
  fun verifyUser(@RequestParam("token") token: String) : ResponseEntity<Unit> {
    if (!registrationService.verifyUser(token)) {
      return ResponseEntity(HttpStatus.CONFLICT)
    }
    return ResponseEntity(HttpStatus.OK)
  }

}