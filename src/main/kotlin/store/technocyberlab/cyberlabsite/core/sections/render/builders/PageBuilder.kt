package store.technocyberlab.cyberlabsite.core.sections.render.builders

import store.technocyberlab.cyberlabsite.core.sections.render.annotations.PageDsl
import store.technocyberlab.cyberlabsite.core.sections.render.entities.Page
import store.technocyberlab.cyberlabsite.core.sections.render.entities.PageSection

@PageDsl
class PageBuilder {
    var title: String = ""
    private val sections = mutableListOf<PageSection>()

    fun section(block: PageSectionBuilder.() -> Unit) {
        sections.add(PageSectionBuilder().apply(block).build())
    }

    fun build(): Page = Page(title, sections)
}