package com.conas.commons.auth.providers

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UsernamePasswordAuthentication
        constructor(val username: String?, val password: String?): Authentication {

    private var authenticated: Boolean = false
    private var principal: Any? = null
    private var details: UserDetails? = null

    override fun getName(): String? {
        return username
    }

    override fun getCredentials(): String? {
        return password
    }

    override fun getPrincipal(): Any? {
        return principal
    }

    fun setPrincipal(principal: Any?) {
        this.principal = principal
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(authenticated: Boolean) {
        this.authenticated = authenticated
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return details?.authorities
    }

    override fun getDetails(): UserDetails? {
        return details
    }
}
