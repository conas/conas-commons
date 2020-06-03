package com.conas.commons.auth.utils

import java.io.ByteArrayInputStream
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.cert.CertificateFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*


object TokenUtils {

    private val keyFactory = KeyFactory.getInstance("RSA")
    private val certificateFactory = CertificateFactory.getInstance("X509");

    fun createPublicKey(publicKey: String): PublicKey {
        return certificateFactory
                    .generateCertificate(ByteArrayInputStream(publicKey.toByteArray())).publicKey
    }

    fun createPrivateKey(privateKey: String): PrivateKey {
        // for some reason private key java implementation can't handle the default format
        val preprocessedKey =
                privateKey
                    .replace("\n", "")
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "");

        val decoded = Base64.getDecoder().decode(preprocessedKey)
        return keyFactory.generatePrivate(PKCS8EncodedKeySpec(decoded))
    }
}