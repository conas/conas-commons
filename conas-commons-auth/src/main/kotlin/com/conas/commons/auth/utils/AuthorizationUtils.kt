package com.conas.commons.auth.utils

import com.conas.commons.auth.enums.AuthorizationType
import com.conas.commons.utils.EnumUtils
import java.util.*


object AuthorizationUtils {

    fun extractType(header: String): AuthorizationType? {
        val split = header.split(" ")
        if(split.isEmpty()) {
            return null;
        }
        return EnumUtils.getByValue(AuthorizationType::class.java, split[0])
    }

    fun extractJwt(header: String): String {
        return replace(header, AuthorizationType.BEARER)
    }

    fun extractUsernamePassword(header: String): Pair<String, String>? {
        val replaced = replace(header, AuthorizationType.BASIC)
        return try {
            val decoded = Base64.getDecoder().decode(replaced)
            val split = String(decoded).split(":")
            Pair(split[0], split[1])
        } catch (e: Exception) {
            null
        }
    }

    private fun replace(header: String, authorizationType: AuthorizationType): String {
        return header.replace(authorizationType.getValue() + " ", "")
    }
}
