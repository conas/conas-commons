package com.conas.commons.rest.error

class ApplicationException : RuntimeException {

    private val _errorBuilders: MutableList<ErrorBuilder> = ArrayList()
    fun errorBuilders() : MutableList<ErrorBuilder> = _errorBuilders

    constructor(vararg errorBuilder: ErrorBuilder) : super() {
        _errorBuilders.addAll(errorBuilder)
    }

    constructor(vararg errorEvent: ErrorEvent) : super() {
        _errorBuilders.addAll(errorEvent.map { e -> ErrorBuilder(e) })
    }
}