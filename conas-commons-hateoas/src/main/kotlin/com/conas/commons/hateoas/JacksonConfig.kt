package com.conas.commons.hateoas

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.hateoas.core.AnnotationRelProvider
import org.springframework.hateoas.hal.Jackson2HalModule
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder


open class JacksonConfig {

    @Bean
    open fun objectMapperBuilder() : Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder()
                .featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .modules(KotlinModule(), Jackson2HalModule())
                .handlerInstantiator(
                        Jackson2HalModule.HalHandlerInstantiator(
                                AnnotationRelProvider(),
                                null,
                                null))
    }
}
