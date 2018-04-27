package com.conas.commons.rest.context

import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import sun.plugin.dom.core.CoreConstants
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


const val CORRELATION_ID_HEADER = "X-Correlation-Id"

open class RequestContextInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest?,
                           response: HttpServletResponse?,
                           handler: Any?): Boolean {

        val correlationId = request?.getHeader(CORRELATION_ID_HEADER) ?: generateRequestId()
        RequestContext.add(RequestContextData(correlationId))
        return true
    }

    override fun postHandle(request: HttpServletRequest?,
                            response: HttpServletResponse?,
                            handler: Any?,
                            modelAndView: ModelAndView?) {
        RequestContext.clear()
    }

    private fun generateRequestId() : String {
        return UUID.randomUUID().toString()
    }
}