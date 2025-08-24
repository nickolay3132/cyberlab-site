package store.technocyberlab.cyberlabsite.core.sections.render

import org.springframework.ui.Model
import store.technocyberlab.cyberlabsite.core.sections.render.builders.PageBuilder

fun page(model: Model, block: PageBuilder.() -> Unit): String {
    val page = PageBuilder().apply(block).build()
    val templates: List<String> = page.sections.map { it.template }

    with(model) {
        addAttribute("title", page.title)
        addAttribute("templates", templates)

        page.sections.forEach { section ->
            addAttribute(section.name, section.data)
        }
    }

    return "layouts/sections_layout"
}