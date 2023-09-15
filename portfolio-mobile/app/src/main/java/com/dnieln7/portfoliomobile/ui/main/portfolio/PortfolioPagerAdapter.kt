package com.dnieln7.portfoliomobile.ui.main.portfolio

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dnieln7.portfoliomobile.ui.main.portfolio.page.ProjectsPage
import com.dnieln7.portfoliomobile.ui.main.portfolio.page.SkillsPage
import com.dnieln7.portfoliomobile.ui.main.portfolio.page.WelcomePage
import com.dnieln7.portfoliomobile.ui.main.portfolio.page.WorkPage

class PortfolioPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabsCreator: Map<Int, () -> Fragment> = mapOf(
        WELCOME_PAGE_INDEX to { WelcomePage() },
        SKILLS_PAGE_INDEX to { SkillsPage() },
        WORK_PAGE_INDEX to { WorkPage() },
        PROJECTS_PAGE_INDEX to { ProjectsPage() }
    )

    override fun getItemCount() = tabsCreator.size

    override fun createFragment(position: Int): Fragment {
        return tabsCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    companion object {
        const val WELCOME_PAGE_INDEX = 0
        const val SKILLS_PAGE_INDEX = 1
        const val WORK_PAGE_INDEX = 2
        const val PROJECTS_PAGE_INDEX = 3
    }
}