package com.conas.commons.rest.error

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


enum class GeneralErrorEvents(override val code: Int,
                              override val message: String) : ErrorEvent {

    NOT_FOUND_ERROR(404, "not.found.error"),
    INTERNAL_SERVER_ERROR(500, "internal.server.error")
}

enum class ValidationErrorEvents(override val code: Int,
                                 override val message: String,
                                 val annotation: Class<out Annotation>) : ErrorEvent {

    NOT_NULL_ERROR(800, "not.null.error", NotNull::class.java),
    NOT_EMPTY_ERROR(801, "not.empty.error", NotEmpty::class.java)
}