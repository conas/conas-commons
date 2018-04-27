package com.conas.commons.rest

import com.conas.commons.rest.error.ErrorResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
open class ResponseAssembler
    @Autowired constructor(private val messageService: MessageService) {

    fun assembleErrorResponse(logRef: String,
                              errorEvent: ErrorEvent,
                              replacements: Array<Any?> = arrayOf(),
                              path: String? = null): ErrorResponse {

        val localizedMessage = messageService.getLocalizedMessage(errorEvent.message(), replacements)
        return ErrorResponse(errorEvent.code(), localizedMessage, logRef, path)
    }
}