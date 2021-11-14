package com.example.demo

data class WorkflowDto(
    val caseId: Long?,
    val memoId: String,
    val process: String,
    val crStatus: String,
    val ralStatus: String,
    val flowStatus: String,
    val workflowStatus: String,
    val retry: Boolean
)