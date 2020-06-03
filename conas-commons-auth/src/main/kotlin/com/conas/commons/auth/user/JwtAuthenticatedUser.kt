package com.conas.commons.auth.user

data class JwtAuthenticatedUser(
    override val userId: Long,
    override val username: String,
    override val email: String,
    override val clientId: String,
    override val scopes: List<String>,
    val jti: String,
    val expiresAt: Long
) : AuthenticatedUser
