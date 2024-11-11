package org.example.controllers

import org.example.exception.ErrorMessage
import org.example.exception.RouteException
import org.example.exception.UserException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(RouteException::class, UserException::class, IllegalArgumentException::class)
    fun handleIllegalStateException(ex: RuntimeException): ResponseEntity<ErrorMessage> {

        val errorMessage = ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            ex.message.orEmpty()
        )

        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
}
