package com.conas.commons.rest

import com.conas.commons.rest.error.*
import com.conas.commons.rest.context.RequestContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.NoHandlerFoundException
import javax.validation.ConstraintViolation


@ControllerAdvice
open class ExceptionHandler
        @Autowired constructor(private val messageService: MessageService,
                               private val errorRegistry: ErrorRegistry) {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ApplicationException::class)
    @ResponseBody
    open fun handleApplicationException(e: ApplicationException)
            : ResponseEntity<ErrorsResponse> {

        val logRef = logException(e)
        val errorResponses = ArrayList<ErrorResponse>()
        for (errorBuilder in e.errorBuilders()) {
            errorResponses.add(assembleErrorResponse(logRef,
                                                     errorBuilder.errorEvent(),
                                                     errorBuilder.replacements()))
        }

        return ResponseEntity(ErrorsResponse(errorResponses),
                              HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    open fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException)
            : ResponseEntity<ErrorsResponse> {

        val logRef = logException(e)
        val errorResponses = ArrayList<ErrorResponse>()
        loop@ for(error in e.bindingResult.allErrors) {
            when (error) {
                is FieldError -> {
                    val violation = error.unwrap(ConstraintViolation::class.java)
                    val mapping = violation.constraintDescriptor.annotation.annotationClass.java.simpleName
                    val errorEvent = errorRegistry.pull(mapping)
                    if(errorEvent == null) {
                        log.error("Missing validation mapping for \"$mapping\"")
                        break@loop;
                    }

                    val path = error.field
                    val replacements = listOf<Any?>(path, error.rejectedValue)

                    errorResponses.add(assembleErrorResponse(logRef,
                                                             errorEvent,
                                                             replacements,
                                                             path))
                }
            }
        }

        return ResponseEntity(ErrorsResponse(errorResponses),
                              HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(e: NoHandlerFoundException) : ResponseEntity<ErrorsResponse> {
        return assembleFromErrorEvent(e,
                                      GeneralErrorEvents.NOT_FOUND_ERROR,
                                      HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    open fun handleException(e: Exception): ResponseEntity<ErrorsResponse> {
        return assembleFromErrorEvent(e,
                                      GeneralErrorEvents.INTERNAL_SERVER_ERROR,
                                      HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun assembleFromErrorEvent(e: Exception,
                                       errorEvent: ErrorEvent,
                                       httpStatus: HttpStatus): ResponseEntity<ErrorsResponse> {

        val logRef = logException(e)
        return ResponseEntity(ErrorsResponse(assembleErrorResponse(logRef, errorEvent)),
                              httpStatus)
    }

    private fun assembleErrorResponse(logRef: String,
                                      errorEvent: ErrorEvent,
                                      replacements: List<Any?> = ArrayList(),
                                      path: String? = null): ErrorResponse {

        val localizedMessage =
                messageService.getLocalizedMessage(errorEvent.message, replacements)

        return ErrorResponse(errorEvent.code,
                             localizedMessage,
                             logRef,
                             path)
    }

    private fun logException(e: Exception) : String {
        val logRef = getLogRef()
        log.error("LogRef: $logRef", e)
        return logRef
    }

    private fun getLogRef(): String {
        return RequestContext.get()?.requestId ?: ""
    }
}
