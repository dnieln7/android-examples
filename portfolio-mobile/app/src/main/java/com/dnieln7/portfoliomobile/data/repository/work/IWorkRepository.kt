package com.dnieln7.portfoliomobile.data.repository.work

import com.dnieln7.portfoliomobile.domain.model.DataResult
import com.dnieln7.portfoliomobile.domain.model.WorkLog

interface IWorkRepository {

    suspend fun getWorkLogs(): DataResult<List<WorkLog>>
}