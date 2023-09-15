package com.dnieln7.portfoliomobile.data.repository.skill

import com.dnieln7.portfoliomobile.domain.model.DataResult
import com.dnieln7.portfoliomobile.domain.model.Skill

interface ISkillsRepository {

    suspend fun getSkills(): DataResult<List<Skill>>
}