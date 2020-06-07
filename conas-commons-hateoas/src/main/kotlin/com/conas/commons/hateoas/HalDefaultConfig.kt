package com.conas.commons.hateoas

import org.springframework.context.annotation.Bean
import org.springframework.hateoas.mediatype.MessageResolver
import org.springframework.hateoas.mediatype.hal.CurieProvider

open class HalDefaultConfig {

    @Bean
    fun curieProvider(): CurieProvider {
        return CurieProvider.NONE
    }

    @Bean
    fun messageResolver(): MessageResolver {
        return MessageResolver.DEFAULTS_ONLY
    }
}