package store.technocyberlab.cyberlabsite.core.sections.render.entities

import store.technocyberlab.cyberlabsite.core.sections.data.SectionData

data class PageSection(
    val name: String,
    val template: String,
    val data: SectionData
)
