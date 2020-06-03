package com.conas.commons.auth.enums

import com.conas.commons.utils.ValueEnum


enum class AuthorizationType(private val enumValue: String) : ValueEnum<String> {
    BEARER("Bearer"),
    BASIC("Basic");

    override fun getValue(): String {
        return enumValue
    }
}
