package com.conas.commons.rest.error

import com.conas.commons.rest.ErrorEvent


class ErrorBuilder(private val _errorEvent: ErrorEvent) {

    fun errorEvent(): ErrorEvent = _errorEvent

    private val _replacements: MutableList<Any> = ArrayList()
    fun replacements():  Array<Any?> = _replacements.toTypedArray()

    fun format(value: Any) : ErrorBuilder {
        _replacements.add(value)
        return this
    }
}