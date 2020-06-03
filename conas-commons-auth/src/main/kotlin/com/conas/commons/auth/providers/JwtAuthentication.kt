package com.conas.commons.auth.providers

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class JwtAuthentication(val jwt: String) : Authentication {

    private var authenticated = false
    private val authorities: Collection<GrantedAuthority>? = null
    private var details: UserDetails? = null
    private val principal: Any? = null

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return authorities
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getDetails(): Any? {
        return details
    }

    override fun getPrincipal(): Any? {
        return principal
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }

    override fun getName(): String? {
        return null
    }

    fun setDetails(details: UserDetails) {
        this.details = details
    }
}