package com.jkojote.trex.jwt

import com.jkojote.trex.user.domain.model.User
import com.jkojote.trex.user.domain.service.jwt.JwtService
import com.jkojote.trex.user.domain.service.user.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(
  private val jwtService: JwtService,
  private val userService: UserService,
) : OncePerRequestFilter() {

  @Throws(IOException::class, ServletException::class)
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    chain: FilterChain
  ) {
    val authorizationHeader = request.getHeader("Authorization")
    if (isOptionsRequest(request)) {
      addCorsHeaders(response)
      return
    }
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      chain.doFilter(request, response)
      return
    }
    addCorsHeaders(response)
    val stringifiedToken = authorizationHeader.replace("Bearer ", "").trim()
    val authentication = getAuthentication(stringifiedToken)
    SecurityContextHolder.getContext().authentication = authentication
    chain.doFilter(request, response)
  }

  private fun addCorsHeaders(response: HttpServletResponse) {
    response.addHeader("Access-Control-Allow-Origin", "*")
    response.addHeader("Access-Control-Allow-Methods", "*")
    response.addHeader("Access-Control-Allow-Headers", "*")
  }

  private fun isOptionsRequest(request: HttpServletRequest): Boolean {
    return "OPTIONS" == request.method
  }

  private fun getAuthentication(stringifiedToken: String): Authentication? {
    try {
      val token = jwtService.parseToken(stringifiedToken)
      return userService
        .finUserByEmail(token.username)
        .map { user -> authenticateUser(user) }
        .orElse(null)
    } catch (e: Exception) {
      return null
    }
  }

  private fun authenticateUser(user: User) : Authentication {
    return UserAuthentication(user)
  }


}
