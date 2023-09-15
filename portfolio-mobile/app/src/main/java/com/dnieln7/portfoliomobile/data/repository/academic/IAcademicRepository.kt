package com.dnieln7.portfoliomobile.data.repository.academic

import com.dnieln7.portfoliomobile.domain.model.AcademicLog
import com.dnieln7.portfoliomobile.domain.model.DataResult

interface IAcademicRepository {

    suspend fun getAcademicLogs(): DataResult<List<AcademicLog>>
}