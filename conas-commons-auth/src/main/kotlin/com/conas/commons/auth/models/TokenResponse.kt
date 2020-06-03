package com.conas.commons.auth.models

import com.conas.commons.auth.enums.TokenType
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty


data class TokenResponse (

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val accessToken: String?,

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val refreshToken: String?,

    @JsonProperty
    val tokenType: TokenType,

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val scope: Collection<String>?,

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val expiresIn: Long?
)