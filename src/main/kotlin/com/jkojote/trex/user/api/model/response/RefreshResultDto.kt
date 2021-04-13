package com.jkojote.trex.user.api.model.response

import com.jkojote.trex.user.domain.service.authentication.RefreshResult

class RefreshResultDto(
  val accessToken: String,
  val refreshToken: String
) {
  companion object {
    fun fromRefreshResult(refreshResult: RefreshResult) : RefreshResultDto {
      return RefreshResultDto(
        accessToken = refreshResult.accessToken,
        refreshToken = refreshResult.refreshToken
      )
    }
  }
}