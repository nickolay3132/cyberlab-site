package store.technocyberlab.cyberlabsite.core.sections.render.builders

import store.technocyberlab.cyberlabsite.core.sections.data.SectionData
import store.technocyberlab.cyberlabsite.core.sections.render.annotations.PageDsl
import store.technocyberlab.cyberlabsite.core.sections.render.entities.PageSection

@PageDsl
class PageSectionBuilder {
    var name: String? = null
    var template: String? = null
    var data: SectionData? = null

    fun build(): PageSection {
        if (name == null || template == null || data == null) {
            throw Exception("Template or data is null")
        }

        return PageSection(name = name!!, template = template!!, data = data!!)
    }
}