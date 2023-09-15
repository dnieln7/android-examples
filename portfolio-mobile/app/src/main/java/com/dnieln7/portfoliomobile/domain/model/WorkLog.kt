package com.dnieln7.portfoliomobile.domain.model

data class WorkLog(
    val name: String,
    val date: String,
    val description: String,
    val activities: List<String>,
)
