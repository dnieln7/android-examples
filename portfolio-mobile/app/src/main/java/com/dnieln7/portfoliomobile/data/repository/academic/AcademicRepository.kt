package com.dnieln7.portfoliomobile.data.repository.academic

import com.dnieln7.portfoliomobile.domain.constant.DataSource
import com.dnieln7.portfoliomobile.domain.model.AcademicLog
import com.dnieln7.portfoliomobile.domain.model.DataResult

class AcademicRepository : IAcademicRepository {
    private val academicLogs = listOf(
        AcademicLog(
            name = "Universidad Veracruzana",
            description = "Bachelor of Software Engineering",
            date = "2017 – 2021",
            url = null
        ),
        AcademicLog(
            name = "Quantum HackMx - Tecnológico de monterrey",
            description = "First place IBM challenge with E-Voting, a solution to take the mexican elections to the digital era focusing on election fraud.",
            date = "April 2021",
            url = "https://firebasestorage.googleapis.com/v0/b/portfolio-dnieln7.appspot.com/o/portfolio-project-files%2Fhackmx-2021.pdf?alt=media"
        )
    )

    override suspend fun getAcademicLogs(): DataResult<List<AcademicLog>> {
        return DataResult(DataSource.LOCAL, true, academicLogs)
    }
}