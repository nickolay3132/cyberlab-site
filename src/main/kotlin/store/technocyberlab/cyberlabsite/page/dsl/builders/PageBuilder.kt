package store.technocyberlab.cyberlabsite.page.dsl.builders

import store.technocyberlab.cyberlabsite.page.dsl.annotations.PageDsl
import store.technocyberlab.cyberlabsite.page.dsl.entities.Page
import store.technocyberlab.cyberlabsite.page.dsl.entities.PageSection

@PageDsl
class PageBuilder {
    var title: String = ""
    var header: String = "main-header"
    var footer: String = "main-footer"
    private val sections = mutableListOf<PageSection>()

    fun section(block: PageSectionBuilder.() -> Unit) {
        sections.add(PageSectionBuilder().apply(block).build())
    }

    fun build(): Page = Page(
        title = title,
        header = header,
        footer = footer,
        sections = sections
    )
}