package com.jkojote.trex.user.domain.service.jwt

interface JwtService {

  fun createToken(username: String) : String

  fun parseToken(stringifiedToken: String) : Jwt

}