package com.conas.commons.conf

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties("conas")
class PropertySourcesProperties {
    var propertySources: Array<String> = arrayOf()
}


@Configuration
open class PropertySourcesConf {

    @Bean
    open fun messageSource(properties: PropertySourcesProperties)
            : ReloadableResourceBundleMessageSource {

        val source = ReloadableResourceBundleMessageSource()
        source.setBasenames(*properties.propertySources)
        source.setDefaultEncoding("UTF-8")
        return source
    }
}


