package com.dnieln7.portfoliomobile.domain.model

import com.dnieln7.portfoliomobile.domain.constant.DataSource

data class DataResult<T>(
    val source: DataSource,
    val success: Boolean,
    val data: T,
    val error: String? = null
)
