package com.jkojote.trex.resource.domain.service

import com.jkojote.trex.resource.domain.model.Resource
import org.springframework.data.jpa.repository.JpaRepository

interface ResourceRepository : JpaRepository<Resource, String> {
}