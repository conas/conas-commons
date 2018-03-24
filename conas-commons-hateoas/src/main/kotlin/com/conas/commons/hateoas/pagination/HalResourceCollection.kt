package com.conas.commons.hateoas.pagination

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.ResourceSupport

abstract class HalResourceCollection<T> : ResourceSupport() {

    protected val embedded = HashMap<String, Collection<T>>()

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("_embedded")
    fun getEmbeddedResources(): Map<String, Collection<T>> {
        return embedded
    }

    fun embedResource(relationship: String, items: Collection<T>) {
        embedded[relationship] = items
    }
}