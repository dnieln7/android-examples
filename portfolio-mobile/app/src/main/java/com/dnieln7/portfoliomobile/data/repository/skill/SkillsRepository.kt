package com.dnieln7.portfoliomobile.data.repository.skill

import com.dnieln7.portfoliomobile.domain.constant.DataSource
import com.dnieln7.portfoliomobile.domain.constant.SkillType
import com.dnieln7.portfoliomobile.domain.model.DataResult
import com.dnieln7.portfoliomobile.domain.model.Skill

class SkillsRepository : ISkillsRepository {

    private val skills = listOf(
        Skill("Kotlin / Java", SkillType.Android),
        Skill("Flutter", SkillType.Android),
        Skill("Android Jetpack", SkillType.Android),
        Skill("Jetpack Compose", SkillType.Android),
        Skill("CI/CD", SkillType.Android),
        Skill("MVVM", SkillType.Android),
        Skill("Dependency Injection", SkillType.Android),
        Skill("NodeJS", SkillType.BackFrontEnd),
        Skill("Angular", SkillType.BackFrontEnd),
        Skill("Spring Boot", SkillType.BackFrontEnd),
        Skill("Sequelize", SkillType.BackFrontEnd),
        Skill("PostgreSQL", SkillType.BackFrontEnd),
        Skill("Firebase", SkillType.Other),
        Skill("Docker", SkillType.Other),
        Skill("Git", SkillType.Other),
        Skill("SOLID", SkillType.Other),
    )

    override suspend fun getSkills(): DataResult<List<Skill>> {
        return DataResult(DataSource.LOCAL, true, skills)
    }
}