package com.conas.commons.auth.user

interface AuthenticatedUser {
    val userId: Long
    val username: String
    val email: String
    val clientId: String
    val scopes: List<String>
}
