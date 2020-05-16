package com.conas.commons.rest.error

import com.conas.commons.rest.ErrorEvent
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


enum class GeneralErrorEvents(private val code: Int,
                              private val message: String) : ErrorEvent {

    NOT_FOUND_ERROR(404, "not.found.error"),
    UNAUTHORIZED(401, "unauthorized"),
    INTERNAL_SERVER_ERROR(500, "internal.server.error");

    override fun code(): Int = code
    override fun message(): String = message
}

enum class ValidationErrorEvents(private val code: Int,
                                 private val message: String,
                                 val annotation: Class<out Annotation>) : ErrorEvent {

    NOT_NULL_ERROR(800, "not.null.error", NotNull::class.java),
    NOT_EMPTY_ERROR(801, "not.empty.error", NotEmpty::class.java);

    override fun code(): Int = code
    override fun message(): String = message
}
