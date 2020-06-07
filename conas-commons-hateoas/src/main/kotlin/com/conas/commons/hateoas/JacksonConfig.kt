package com.conas.commons.hateoas

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.hateoas.mediatype.MessageResolver
import org.springframework.hateoas.mediatype.hal.CurieProvider
import org.springframework.hateoas.mediatype.hal.DefaultCurieProvider
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule
import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder


open class JacksonConfig {

    @Bean
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    open fun objectMapperBuilder(curieProvider: CurieProvider,
                                 messageResolver: MessageResolver) : Jackson2ObjectMapperBuilder {

        return Jackson2ObjectMapperBuilder()
                .featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .modules(KotlinModule(), Jackson2HalModule())
                .handlerInstantiator(
                    Jackson2HalModule.HalHandlerInstantiator(
                        AnnotationLinkRelationProvider(),
                        curieProvider,
                        messageResolver))
    }
}
