package com.conas.commons.rest.context

import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


open class RequestContextInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest?,
                           response: HttpServletResponse?,
                           handler: Any?): Boolean {

        RequestContext.add(RequestContextData(generateRequestId()))
        return true;
    }

    override fun postHandle(request: HttpServletRequest?,
                            response: HttpServletResponse?,
                            handler: Any?,
                            modelAndView: ModelAndView?) {

        RequestContext.clear()
    }

    private fun generateRequestId() : String {
        return UUID.randomUUID().toString();
    }
}