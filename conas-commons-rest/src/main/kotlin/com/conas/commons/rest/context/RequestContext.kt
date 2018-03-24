package com.conas.commons.rest.context


data class RequestContextData(
    val requestId : String
)

object RequestContext {
    private val context : ThreadLocal<RequestContextData> = ThreadLocal()

    @JvmStatic
    fun add(value: RequestContextData) {
        context.set(value)
    }

    @JvmStatic
    fun get(): RequestContextData? = context.get()

    @JvmStatic
    fun clear() {
        context.remove()
    }
}