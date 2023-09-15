package com.dnieln7.portfoliomobile.data.server.project

import com.dnieln7.portfoliomobile.domain.model.Project
import retrofit2.http.GET

interface PortfolioApi {

    @GET("projects")
    suspend fun getProjects(): List<Project>
}