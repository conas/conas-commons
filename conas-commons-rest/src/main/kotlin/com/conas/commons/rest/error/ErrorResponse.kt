package com.conas.commons.rest.error

import com.conas.commons.hateoas.pagination.HalResourceCollection
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.ResourceSupport

data class ErrorResponse(

    @JsonProperty
    override val code: Int,

    @JsonProperty
    override val message: String,

    @JsonProperty
    val logref: String,

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val path: String? = null

) : ResourceSupport(), ErrorEvent {

    constructor(errorEvent: ErrorEvent,
                logRef: String): this(errorEvent.code, errorEvent.message, logRef)
}

class ErrorsResponse : HalResourceCollection<ErrorResponse> {

    constructor() : super()
    constructor(item: ErrorResponse) : this(listOf(item))
    constructor(items: Collection<ErrorResponse>) : super() {
        embedResource("errors", items)
    }
}