package com.conas.commons.rest.error

interface ErrorEvent {
    val code: Int
    val message: String

    fun format(value: Any): ErrorBuilder {
        return ErrorBuilder(this)
                .format(value)
    }
}

class ErrorBuilder(private val _errorEvent: ErrorEvent) {

    fun errorEvent(): ErrorEvent = _errorEvent

    private val _replacements: MutableList<Any> = ArrayList()
    fun replacements():  MutableList<Any> = _replacements

    fun format(value: Any) : ErrorBuilder {
        _replacements.add(value)
        return this
    }
}