package com.jkojote.trex.user.api

import com.jkojote.trex.user.api.dto.AuthenticationDto
import com.jkojote.trex.user.api.dto.RefreshDto
import com.jkojote.trex.user.domain.service.authentication.AuthenticationFailedException
import com.jkojote.trex.user.domain.service.authentication.AuthenticationResult
import com.jkojote.trex.user.domain.service.authentication.AuthenticationService
import com.jkojote.trex.user.domain.service.authentication.RefreshResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.security.auth.RefreshFailedException

@RestController
@RequestMapping(path = ["/api/user/authentication"], produces = ["application/json"])
class AuthenticationApi(private val authenticationService: AuthenticationService) {

  @PostMapping
  fun authenticate(@RequestBody authenticationDto: AuthenticationDto) : ResponseEntity<AuthenticationResult> {
    val authenticationInput = AuthenticationService.AuthenticationInput(
      email = authenticationDto.email,
      password = authenticationDto.password
    )

    try {
      val authenticationResult = authenticationService.authenticate(authenticationInput)
      return ResponseEntity(authenticationResult, HttpStatus.OK)
    } catch (e: AuthenticationFailedException) {
      return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
  }

  @PostMapping("refresh")
  fun refresh(@RequestBody refreshDto: RefreshDto) : ResponseEntity<RefreshResult> {
    try {
      val refreshResult = authenticationService.refresh(refreshDto.refreshToken)
      return ResponseEntity(refreshResult, HttpStatus.OK)
    } catch (e: RefreshFailedException) {
      return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
  }

}