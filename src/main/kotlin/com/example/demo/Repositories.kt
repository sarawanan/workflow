package com.example.demo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

sealed interface MemoRepo : CrudRepository<WorkFlow, Long> {
    override fun findAll(): List<WorkFlow>

    fun findByMemoId(uuid: UUID): WorkFlow = findByMemoId(uuid)

    @Query("SELECT t FROM workflow t WHERE t.workflowStatus = false")
    fun findAllByFailedStatus(): List<WorkFlow>
}