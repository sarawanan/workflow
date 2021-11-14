package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class DbInitializer @Autowired constructor(val repo: MemoRepo) {
    @Bean
    fun initialize() = ApplicationRunner {
        repo.save(
            WorkFlow(
                memoId = UUID.randomUUID(),
                process = Process.INITIATE.function,
                crStatus = true,
                ralStatus = true,
                flowStatus = true,
                workflowStatus = true
            )
        )
    }
}