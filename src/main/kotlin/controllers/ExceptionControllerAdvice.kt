package org.example.controllers

import org.example.exception.AuthException
import org.example.exception.RouteException
import org.example.exception.UserException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(
        RouteException::class,
        UserException::class,
        AuthException::class,
        IllegalArgumentException::class,
        UsernameNotFoundException::class
    )
    fun handleIllegalStateException(ex: RuntimeException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }
}
