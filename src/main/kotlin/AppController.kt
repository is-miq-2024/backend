package org.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AppController {
    @GetMapping("/hello")
    fun helloKotlin(): String {
        return "hello world"
    }
}