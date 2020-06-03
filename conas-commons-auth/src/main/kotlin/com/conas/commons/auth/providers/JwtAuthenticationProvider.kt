package com.conas.commons.auth.providers

import com.conas.commons.auth.UnauthorizedException
import com.conas.commons.auth.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component


@Component
open class JwtAuthenticationProvider
        @Autowired constructor(val oAuth2Service: AuthService) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val jwtAuthentication = authentication as JwtAuthentication
        try {
            oAuth2Service.tokenInfo(jwtAuthentication.jwt)
            jwtAuthentication.isAuthenticated = true
        } catch (e: Exception) {
            throw UnauthorizedException();
        }
        return jwtAuthentication
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication.isAssignableFrom(JwtAuthentication::class.java)
    }
}