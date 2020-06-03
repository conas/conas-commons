package com.clerk.auth.core.providers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
open class UsernamePasswordAuthenticationProvider
    @Autowired constructor(private val userDetailsService: UserDetailsService,
                           private val passwordEncoder: PasswordEncoder) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val usernamePasswordAuth = authentication as UsernamePasswordAuthentication
        val userDetails = userDetailsService.loadUserByUsername(usernamePasswordAuth.name)

        val passwordMatches =
                passwordEncoder
                        .matches(usernamePasswordAuth.getCredentials() as String,
                                 userDetails.password)

        if (!passwordMatches) {
            throw BadCredentialsException("Bad credentials")
        }

        usernamePasswordAuth.principal = userDetails
        usernamePasswordAuth.isAuthenticated = true
        return usernamePasswordAuth
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication!!.isAssignableFrom(UsernamePasswordAuthentication::class.java)
    }
}