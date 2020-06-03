package com.conas.commons.auth.services

import com.conas.commons.auth.models.TokenInfoResponse
import com.conas.commons.auth.models.TokenRequest
import com.conas.commons.auth.models.TokenResponse


interface AuthService {

    fun token(tokenRequest: TokenRequest) : TokenResponse
    fun tokenInfo(authorizationHeader: String) : TokenInfoResponse
}