package com.conas.commons.rest.error

import com.conas.commons.rest.ErrorEvent


class APIException : RuntimeException {

    private val _errorBuilders: MutableList<ErrorBuilder> = ArrayList()
    fun errorBuilders() : MutableList<ErrorBuilder> = _errorBuilders

    constructor(vararg errorBuilder: ErrorBuilder) : super() {
        _errorBuilders.addAll(errorBuilder)
    }

    constructor(vararg errorEvent: ErrorEvent) : super() {
        _errorBuilders.addAll(errorEvent.map { e -> ErrorBuilder(e) })
    }
}