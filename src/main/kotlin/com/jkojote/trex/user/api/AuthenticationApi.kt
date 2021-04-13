package com.jkojote.trex.user.api

import com.jkojote.trex.user.api.model.request.AuthenticateRequestDto
import com.jkojote.trex.user.api.model.request.RefreshRequestDto
import com.jkojote.trex.user.api.model.response.AuthenticationResultDto
import com.jkojote.trex.user.api.model.response.RefreshResultDto
import com.jkojote.trex.user.domain.service.authentication.AuthenticationFailedException
import com.jkojote.trex.user.domain.service.authentication.AuthenticationService
import com.jkojote.trex.util.mapTo
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
  fun authenticate(@RequestBody requestDto: AuthenticateRequestDto) : ResponseEntity<AuthenticationResultDto> {
    val authenticationInput = AuthenticationService.AuthenticationInput(
      email = requestDto.email,
      password = requestDto.password
    )

    try {
      val authenticationResult = authenticationService
        .authenticate(authenticationInput)
        .mapTo { AuthenticationResultDto.fromAuthenticationResult(this) }

      return ResponseEntity(authenticationResult, HttpStatus.OK)
    } catch (e: AuthenticationFailedException) {
      return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
  }

  @PostMapping("refresh")
  fun refresh(@RequestBody requestDto: RefreshRequestDto) : ResponseEntity<RefreshResultDto> {
    try {
      val refreshResult = authenticationService
        .refresh(requestDto.refreshToken)
        .mapTo { RefreshResultDto.fromRefreshResult(this) }

      return ResponseEntity(refreshResult, HttpStatus.OK)
    } catch (e: RefreshFailedException) {
      return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
  }
}