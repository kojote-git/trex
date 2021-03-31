package com.jkojote.trex.resource.domain.service

import com.jkojote.trex.resource.domain.model.Content
import com.jkojote.trex.resource.domain.model.Resource
import java.io.InputStream
import java.util.*

interface ResourceService {

  fun createResource(input: CreateResourceInput) : Resource

  fun deleteResource(resource: Resource)

  fun findResource(resourceId: String) : Optional<Resource>

  fun saveContent(resource: Resource, content: InputStream)

  fun getContent(resource: Resource) : Optional<Content>
}