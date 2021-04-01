package com.jkojote.trex.config

import com.jkojote.trex.config.jwt.JwtAuthorizationFilter
import com.jkojote.trex.user.domain.service.jwt.JwtService
import com.jkojote.trex.user.domain.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.config.annotation.web.builders.WebSecurity




@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

  @Autowired
  private lateinit var jwtService: JwtService

  @Autowired
  private lateinit var userService: UserService


  override fun configure(http: HttpSecurity) {
    val authorizationFilter = JwtAuthorizationFilter(jwtService, userService)

    http
      .csrf().disable()
      .authorizeRequests()
        .antMatchers("/api/user/registration/**").permitAll()
        .antMatchers("/api/user/authentication/**").permitAll()

        // Place API
        .antMatchers(HttpMethod.POST, "/api/place").hasRole("ADMIN")
        .antMatchers(HttpMethod.PUT, "/api/place/{id}/thumbnail").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/api/place/{id}/photo").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/api/place/{id}/photo/{resourceId}").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/api/place/search/nearset").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/api/place/{id}").authenticated()


        .anyRequest().authenticated()
      .and()
        .addFilterBefore(authorizationFilter, BasicAuthenticationFilter::class.java)
        .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
        .exceptionHandling()
          .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
  }

  @Bean
  fun passwordEncoder() : PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  override fun configure(web: WebSecurity) {
    web.ignoring().antMatchers(
      "/v2/api-docs",
      "/configuration/ui",
      "/swagger-resources/**",
      "/configuration/security",
      "/swagger-ui.html",
      "/webjars/**"
    )
  }

}