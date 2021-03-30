package com.jkojote.trex.place.domain.service.content

import org.springframework.core.io.Resource
import java.io.InputStream
import java.util.*

interface ContentService {

  fun saveContent(content: InputStream) : ContentId

  fun deleteContent(contentId: ContentId)

  fun getContent(contentId: ContentId) : Optional<Resource>

}