package com.conas.commons.rest.error

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component


@Component
open class ErrorRegistry : InitializingBean {

    private val errors : HashMap<String, ErrorEvent> = HashMap()

    fun push(id: String, errorEvent: ErrorEvent) {
        errors[id] = errorEvent
    }

    fun pull(id: String) : ErrorEvent? = errors[id]

    override fun afterPropertiesSet() {
        errors.putAll(
                ValidationErrorEvents
                        .values()
                        .associateBy({ it.annotation.simpleName }, { it } ))
    }
}