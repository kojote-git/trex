package com.jkojote.trex.user.domain.service.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Service
class JwtServiceImpl : JwtService {
  private val key: Key
  private val jwtTokenLifeTimeInMinutes: Int

  constructor(
    @Value("\${jwt.token-lifetime-in-minutes:5}")
    jwtTokenLifeTimeInMinutes: Int,
    @Value("\${jwt.secret:#{null}}")
    jwtSecret: String?
  ) {
    this.key = if (jwtSecret != null) Keys.hmacShaKeyFor(jwtSecret.toByteArray()) else Keys.secretKeyFor(
      SignatureAlgorithm.HS512
    )
    this.jwtTokenLifeTimeInMinutes = jwtTokenLifeTimeInMinutes
  }

  override fun createToken(username: String) : String {
    return Jwts.builder()
      .setSubject(username)
      .setExpiration(getExpirationTimeFromNow())
      .signWith(key)
      .compact()
  }

  override fun parseToken(stringifiedToken: String): Jwt {
    val claims = Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(stringifiedToken)
    return Jwt(
      username = claims.body.subject,
      expirationTime = gmtDateToZonedDateTime(claims.body.expiration)
    )
  }

  private fun getExpirationTimeFromNow() : Date {
    return zonedDateTimeToGmtDate(
      ZonedDateTime.now().plusMinutes(jwtTokenLifeTimeInMinutes.toLong())
    )
  }

  private fun zonedDateTimeToGmtDate(zonedDateTime: ZonedDateTime) : Date {
    val epochMilliGmt = zonedDateTime
      .toInstant()
      .atZone(ZoneId.of("GMT"))
      .toInstant()
      .toEpochMilli()
    return Date(epochMilliGmt)
  }

  private fun gmtDateToZonedDateTime(date: Date) : ZonedDateTime {
    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(date.time), ZoneId.of("GMT"))
  }
}