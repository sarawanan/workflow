package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HttpController @Autowired constructor(
    val service: CommonService,
) {
    @PostMapping("/initiate")
    fun initiateMemo(): HttpStatus {
        service.initiateMemo()
        return HttpStatus.OK
    }

    @PostMapping("/retry-memo")
    fun retryMemo(@RequestParam id: String): HttpStatus {
        service.initiateMemo(id)
        return HttpStatus.OK
    }

    @PostMapping("/reset-timer")
    fun resetTimer() {
        service.resetTimer()
    }
}