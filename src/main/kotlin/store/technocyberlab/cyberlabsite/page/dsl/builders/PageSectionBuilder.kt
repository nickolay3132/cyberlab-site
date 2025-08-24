package store.technocyberlab.cyberlabsite.page.dsl.builders

import store.technocyberlab.cyberlabsite.core.sections.data.SectionData
import store.technocyberlab.cyberlabsite.page.dsl.annotations.PageDsl
import store.technocyberlab.cyberlabsite.page.dsl.entities.PageSection

@PageDsl
class PageSectionBuilder {
    var name: String? = null
    var template: String? = null
    var fragment: String = "section"
    var data: SectionData? = null

    fun build(): PageSection {
        if (name == null || template == null || data == null) {
            throw Exception("Template or data is null")
        }

        return PageSection(
            name = name!!,
            template = template!!,
            fragment = fragment,
            data = data!!
        )
    }
}