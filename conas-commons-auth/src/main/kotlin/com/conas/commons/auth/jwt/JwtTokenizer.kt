package com.conas.commons.auth.jwt

import com.conas.commons.auth.user.AuthenticatedUser
import com.conas.commons.auth.user.JwtAuthenticatedUser
import com.conas.commons.auth.utils.TokenUtils
import org.jose4j.jws.AlgorithmIdentifiers
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.NumericDate
import org.jose4j.jwt.consumer.InvalidJwtException
import org.jose4j.lang.JoseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.security.PrivateKey
import java.security.PublicKey

// JWT fields
private const val USER_ID = "uid"
private const val CLIENT_ID = "auid"
private const val USERNAME = "usrn"
private const val EMAIL = "eml"
private const val ROLES = "rls"
private const val SCOPES = "scps"

@Component
open class JwtTokenizer @Autowired constructor(private val jwtProperties: JwtProperties) {

    private val publicKey: PublicKey
    private val privateKey: PrivateKey

    init {
        try {
            publicKey = TokenUtils.createPublicKey(jwtProperties.publicKey)
            privateKey = TokenUtils.createPrivateKey(jwtProperties.privateKey)
        } catch (e: Exception) {
            throw JwtConfigurationException("Failed to create public/private key pair", e)
        }
    }

    fun generateToken(authenticatedUser: AuthenticatedUser, jti: String, expireIn: Long) : String {
        val claims = JwtClaims()
        claims.issuer = jwtProperties.issuer
        claims.jwtId = jti

        claims.setClaim(USER_ID, authenticatedUser.userId)
        claims.setClaim(CLIENT_ID, authenticatedUser.clientId)
        claims.setClaim(USERNAME, authenticatedUser.username)
        claims.setClaim(EMAIL, authenticatedUser.email)
        claims.setClaim(SCOPES, authenticatedUser.scopes)

        val time = System.currentTimeMillis()
        claims.expirationTime = NumericDate.fromMilliseconds(time + expireIn)
        claims.issuedAt = NumericDate.fromMilliseconds(time)

        val jws = JsonWebSignature()
        jws.payload = claims.toJson()
        jws.key = privateKey
        jws.algorithmHeaderValue = AlgorithmIdentifiers.RSA_USING_SHA512

        try {
            return jws.compactSerialization
        } catch (e: JoseException) {
            throw JwtCreationException("Failed to create jwt token")
        }
    }

    private fun getClaims(jwt: String) : JwtClaims {
        try {
            val jws = JsonWebSignature()
            jws.compactSerialization = jwt
            jws.key = publicKey
            return JwtClaims.parse(jws.payload)
        } catch (e: JoseException) {
            throw JwtVerificationException("Failed to verify token signature")
        } catch (e: InvalidJwtException) {
            throw JwtVerificationException("Invalid jwt")
        }
    }

    @SuppressWarnings("UNCHECKED_CAST")
    fun validateToken(token: String): JwtAuthenticatedUser {
        val claims = getClaims(token)

        return JwtAuthenticatedUser(
            jti = claims.jwtId,
            userId = claims.getClaimValue(USER_ID) as Long,
            username = claims.getClaimValue(USERNAME) as String,
            clientId = claims.getClaimValue(CLIENT_ID) as String,
            email = claims.getClaimValue(EMAIL) as String,
            scopes = claims.getClaimValue(SCOPES) as List<String>,
            expiresAt = claims.expirationTime.valueInMillis
        )
    }
}
