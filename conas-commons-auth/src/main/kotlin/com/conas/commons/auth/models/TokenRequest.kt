package com.conas.commons.auth.models

import com.conas.commons.auth.enums.GrantType
import com.conas.commons.utils.EnumUtils
import com.fasterxml.jackson.annotation.JsonProperty

data class TokenRequest(

    @JsonProperty
    val username: String?,

    @JsonProperty
    val password: String?,
    
    @JsonProperty("client_id")
    val clientId: String?,
    
    @JsonProperty("client_secret")
     val clientSecret: String?,
    
    @JsonProperty("grant_type")
    val grantType: String?,
    
    @JsonProperty("scope")
    val scope: String?,
    
    @JsonProperty("state")
    val state: String?,
    
    @JsonProperty("refresh_token")
    val refreshToken: String?
) {
    fun getGrantType() : GrantType? {
        return EnumUtils.getByValue(GrantType::class.java, this.grantType)
    }
}