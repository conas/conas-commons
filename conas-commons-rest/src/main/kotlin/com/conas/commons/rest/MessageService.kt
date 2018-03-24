package com.conas.commons.rest

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component


@Component
class MessageService
    @Autowired constructor(private val messageSource: MessageSource) {

    val log = LoggerFactory.getLogger(javaClass)

    fun getLocalizedMessage(message: String, vararg replacements: Any): String {
        return try {
            messageSource.getMessage(message,
                                     replacements,
                                     LocaleContextHolder.getLocale())

        } catch (e: Exception) {
            log.error("Message for key: \"{}\" not found", message)
            message
        }
    }
}