package com.conas.commons.auth.services

import com.conas.commons.auth.models.TokenInfo


interface AuthService {

    //fun token(authentication: Authentication) : TokenResponse
    fun tokenInfo(authorizationHeader: String) : TokenInfo
}