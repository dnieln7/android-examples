package com.dnieln7.portfoliomobile.data.repository.work

import com.dnieln7.portfoliomobile.domain.constant.DataSource
import com.dnieln7.portfoliomobile.domain.model.DataResult
import com.dnieln7.portfoliomobile.domain.model.WorkLog

class WorkRepository : IWorkRepository {
    private val workLogs = listOf(
        WorkLog(
            name = "Rookmotion",
            date = "2021 - now (1+ years)",
            description = "Heart rate monitoring system for live and remote workouts.",
            activities = listOf(
                "RookMotion  app with RookLink SDK (Kotlin - Android - Maven).",
                "RookBLE package (Flutter - Android/IOS - Pubdev).",
                "RookHealthConnect plugin (Flutter - Android - Pubdev).",
                "Providing Docs, Technical support and Demos to clients.",
            )
        )
    )

    override suspend fun getWorkLogs(): DataResult<List<WorkLog>> {
        return DataResult(DataSource.LOCAL, true, workLogs)
    }
}