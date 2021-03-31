package com.jkojote.trex.user.domain.model

import org.springframework.security.core.GrantedAuthority

enum class Role : GrantedAuthority {
  USER {
    override fun getAuthority(): String {
      return "ROLE_${USER.name}"
    }
  },

  ADMIN {
    override fun getAuthority(): String {
      return "ROLE_${ADMIN.name}"
    }
  }
}