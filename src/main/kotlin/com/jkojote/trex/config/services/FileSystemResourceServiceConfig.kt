package com.jkojote.trex.config.services

import com.jkojote.trex.resource.domain.service.ResourceRepository
import com.jkojote.trex.resource.domain.service.ResourceService
import com.jkojote.trex.resource.domain.service.fs.FileSystemResourceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.io.File

@Configuration
@Profile("enable-fs")
class FileSystemResourceServiceConfig {

  @Value("\${resources.directory}")
  private lateinit var resourcesDirectory: File

  @Autowired
  private lateinit var resourceRepository: ResourceRepository

  @Bean
  fun fileSystemResourcesService() : ResourceService {
    return FileSystemResourceService(resourceRepository, resourcesDirectory)
  }

}