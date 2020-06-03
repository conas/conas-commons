package com.conas.commons.hateoas

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule
import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
open class JacksonConfig {

    @Bean
    open fun objectMapperBuilder() : Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder()
                .featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .modules(KotlinModule(), Jackson2HalModule())
                .handlerInstantiator(
                    Jackson2HalModule.HalHandlerInstantiator(
                        AnnotationLinkRelationProvider(),
                        null,
                        null))
    }
}
