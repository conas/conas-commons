package com.conas.commons.auth.enums

import com.conas.commons.utils.ValueEnum


enum class TokenType(val label: String, val enumValue: String) : ValueEnum<String> {
    BEARER("bearer", "Bearer");

    override fun getValue(): String {
        return enumValue
    }
}