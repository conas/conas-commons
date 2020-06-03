package com.conas.commons.auth

import com.conas.commons.auth.jwt.JwtProperties
import com.conas.commons.auth.jwt.JwtTokenizer
import com.conas.commons.auth.user.AuthenticatedUser
import com.google.common.io.Resources
import org.hamcrest.MatcherAssert
import org.hamcrest.collection.IsIterableContainingInOrder
import org.junit.Assert.*
import org.junit.Test


open class JwtTokenizerTest {

    private val jwtProperties: JwtProperties
    private val jwtTokenizer: JwtTokenizer

    init {
        jwtProperties = JwtProperties(
                issuer = "test",
                publicKey = loadResource("keys/test.pem"),
                privateKey = loadResource("keys/test.key"),
                tokenLife = "10m")

        jwtTokenizer = JwtTokenizer(jwtProperties)
    }

    @Test
    fun testGenerateToken() {
        val jti = "jti"
        val expireIn = 10000L

        val authenticateUser = authenticatedUserFixture()
        val jwt = jwtTokenizer.generateToken(authenticateUser, jti, expireIn)
        val jwtAuthenticatedUser = jwtTokenizer.validateToken(jwt)

        assertNotNull(jwtAuthenticatedUser)
        assertEquals(jti, jwtAuthenticatedUser.jti)
        assertEquals(authenticateUser.userId, jwtAuthenticatedUser.userId)
        assertEquals(authenticateUser.username, jwtAuthenticatedUser.username)
        assertEquals(authenticateUser.clientId, jwtAuthenticatedUser.clientId)
        assertEquals(authenticateUser.email, jwtAuthenticatedUser.email)
        MatcherAssert.assertThat(
            authenticateUser.scopes,
            IsIterableContainingInOrder.contains(*jwtAuthenticatedUser.scopes.toTypedArray()))

        // improve this check in the future
        assertTrue(jwtAuthenticatedUser.expiresAt > System.currentTimeMillis())
    }

    private fun loadResource(keyLocation: String): String {
        return Resources.toString(javaClass.classLoader.getResource(keyLocation), Charsets.UTF_8)
    }

    private fun authenticatedUserFixture() : AuthenticatedUser {
        return AuthenticatedUserModel(
                userId = 10L,
                username = "username",
                email = "email",
                clientId = "clientId",
                scopes = arrayListOf("scope1", "scope2")
        )
    }
}

data class AuthenticatedUserModel(
    override val userId: Long,
    override val username: String,
    override val email: String,
    override val clientId: String,
    override val scopes: ArrayList<String>
) : AuthenticatedUser