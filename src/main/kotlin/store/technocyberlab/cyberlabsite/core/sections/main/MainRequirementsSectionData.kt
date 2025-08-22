package store.technocyberlab.cyberlabsite.core.sections.main

import store.technocyberlab.cyberlabsite.core.sections.SectionData

data class MainRequirementsSectionData(
    val title: String,
    val table: RequirementsTable
) : SectionData {
    data class RequirementsTable(
        val head: List<String>,
        val body: List<RequirementsTableNode>
    )

    data class RequirementsTableNode(
        val component: String,
        val minimum: String,
        val recommended: String,
        val colspan: Int = 1
    )
}
