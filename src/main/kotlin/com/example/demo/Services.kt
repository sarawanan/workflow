package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

var startTime: LocalDateTime = LocalDateTime.now()

@Service
class ExternalService {
    @Retryable(
        value = [HttpServerErrorException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1000),
        exclude = [HttpClientErrorException::class]
    )
    fun initiate(serviceName: String, lapseTime: Int): HttpStatus {
        if (ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()) < lapseTime) {
            println("$serviceName is DOWN. Returning 502 BAD Gateway")
            throw HttpServerErrorException(HttpStatus.BAD_GATEWAY)
        } else {
            println("$serviceName service is UP. Returning 200 OK")
        }
        return HttpStatus.OK
    }

    @Recover
    fun recover(exception: HttpServerErrorException): HttpStatus {
        return HttpStatus.BAD_GATEWAY
    }
}

@Service
class CommonService @Autowired constructor(
    val repo: MemoRepo,
    val cr: ExternalService,
    val ral: ExternalService,
    val flow: ExternalService
) {
    fun getAllMemos() = repo.findAll()

    fun initiateMemo(memoId: String? = null) {
        val workflow =
            if (memoId == null) {
                repo.save(WorkFlow(
                    memoId = UUID.randomUUID(),
                    process = Process.INITIATE.name
                ))
            } else {
                repo.findByMemoId(UUID.fromString(memoId))
            }
        workflow.crStatus = cr.initiate("Credit Request", 6) == HttpStatus.OK
        workflow.ralStatus = ral.initiate("RAL", 4) == HttpStatus.OK
        workflow.flowStatus = flow.initiate("CAP Flow", 15) == HttpStatus.OK
        workflow.workflowStatus =
            workflow.crStatus && workflow.ralStatus && workflow.flowStatus
        repo.save(workflow)
    }

    fun retry() {
        val failedMemos = repo.findAllByFailedStatus()
        if (failedMemos.isNotEmpty()) {
            println("Scheduler scanned & found ${failedMemos.size} FAILED memo(s)")
            failedMemos.forEach {
                println("Retrying memo ID::${it.memoId} - case ID::${it.caseId}")
                if (it.process == Process.INITIATE.name) {
                    initiateMemo(it.memoId.toString())
                }
            }
        }
    }

    fun resetTimer() {
        startTime = LocalDateTime.now();
    }
}