package com.conas.commons.auth.jwt


import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class JwtProperties(

    @Value("\${jwt.issuer}")
    val issuer: String,

    @Value("\${jwt.public.key}")
    val publicKey: String,

    @Value("\${jwt.private.key}")
    val privateKey: String,

    @Value("\${jwt.tokenLife}")
    val tokenLife: String
)
