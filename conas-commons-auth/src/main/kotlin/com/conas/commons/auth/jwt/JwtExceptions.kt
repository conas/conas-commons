package com.conas.commons.auth.jwt


open class JwtException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, e: Exception) : super(message, e)
}

class JwtVerificationException(message: String) : JwtException(message)
class JwtCreationException(message: String) : JwtException(message)
class JwtConfigurationException(message: String, e: Exception) : JwtException(message, e)
