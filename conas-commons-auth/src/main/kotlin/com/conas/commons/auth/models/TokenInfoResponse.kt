package com.conas.commons.auth.models

import com.fasterxml.jackson.annotation.JsonProperty


data class TokenInfoResponse(
        
    @JsonProperty
    val jti: String? = null,

    @JsonProperty
    val userId: Long? = null,

    @JsonProperty
    val username: String? = null,

    @JsonProperty
    val email: String? = null,

    @JsonProperty
    val clientId: String? = null,

    @JsonProperty
    val expiresAt: Long? = null,

    @JsonProperty
    val roles: List<String>? = null
)