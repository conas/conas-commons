package com.conas.commons.auth

import com.conas.commons.auth.utils.AuthorizationUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Base64


open class AuthorizationUtilsTest {

    @Test
    fun testExtractJwt() {
        assertEquals("jwt", AuthorizationUtils.extractJwt("Bearer jwt"))
    }

    @Test
    fun testExtractUsernamePassword() {
        val encoded = Base64.getEncoder().encode("username:password".toByteArray())
        val usernamePassword = AuthorizationUtils.extractUsernamePassword("Basic ${String(encoded)}")

        assertEquals("username", usernamePassword?.first)
        assertEquals("password", usernamePassword?.second)
    }
}
