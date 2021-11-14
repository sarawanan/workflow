package com.example.demo

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "workflow")
data class WorkFlow(
    @Id @GeneratedValue val caseId: Long? = null,
    val memoId: UUID,
    val process: String,
    var crStatus: Boolean = false,
    var ralStatus: Boolean = false,
    var flowStatus: Boolean = false,
    var workflowStatus: Boolean = false,
) {
    fun toDto() = WorkflowDto(
        caseId = this.caseId,
        memoId = this.memoId.toString(),
        process = this.process,
        crStatus = if (this.crStatus)  "SUCCESS" else "FAILED",
        ralStatus = if (this.ralStatus)  "SUCCESS" else "FAILED",
        flowStatus = if (this.flowStatus) "SUCCESS" else "FAILED",
        workflowStatus = if (this.workflowStatus) "SUCCESS" else "FAILED",
        retry = this.workflowStatus
    )
}