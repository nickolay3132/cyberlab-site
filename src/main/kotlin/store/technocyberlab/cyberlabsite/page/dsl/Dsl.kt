package store.technocyberlab.cyberlabsite.page.dsl

import org.springframework.ui.Model
import store.technocyberlab.cyberlabsite.page.dsl.builders.PageBuilder

fun page(model: Model, block: PageBuilder.() -> Unit): String {
    val page = PageBuilder().apply(block).build()
    val templates: List<List<String>> = page.sections.map { listOf(it.template, it.fragment) }

    with(model) {
        addAttribute("title", page.title)
        addAttribute("header", page.header)
        addAttribute("footer", page.footer)
        addAttribute("templates", templates)

        page.sections.forEach { section ->
            addAttribute(section.name, section.data)
        }
    }

    return "layouts/sections_layout"
}