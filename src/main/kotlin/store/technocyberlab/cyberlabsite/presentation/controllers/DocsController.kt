package store.technocyberlab.cyberlabsite.presentation.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import store.technocyberlab.cyberlabsite.core.repositories.DocItemRepository
import store.technocyberlab.cyberlabsite.core.repositories.SectionRepository
import store.technocyberlab.cyberlabsite.core.sections.data.MetaSectionData
import store.technocyberlab.cyberlabsite.infrastructure.entities.extensions.typedData

@Controller
@RequestMapping("/docs")
class DocsController(
    val sectionRepository: SectionRepository,
    val docItemRepository: DocItemRepository,
) : BaseController() {

    @GetMapping
    fun index(model: Model): String {
        val meta = sectionRepository
            .findByPageAndSection("docs", "meta")
            ?.typedData<MetaSectionData>()
        meta?.url = getUrl()

        val docItems = docItemRepository.findAllByIsActiveTrue()

        with(model) {
            addAttribute("page", "docs")
            addAttribute("meta", meta)
            addAttribute("docItems", docItems)
        }
        return "pages/docs"
    }
}