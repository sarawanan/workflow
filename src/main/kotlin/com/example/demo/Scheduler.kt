package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler @Autowired constructor(val service: CommonService) {
    @Scheduled(fixedRate = 5000)
    fun retry() = service.retry()
}