package store.technocyberlab.cyberlabsite.presentation.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import store.technocyberlab.cyberlabsite.infrastructure.entities.extensions.typedData
import store.technocyberlab.cyberlabsite.core.repositories.SectionRepository
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainAboutSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainClmtSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainCtaSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainFeaturesSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainRequirementsSectionData

@Controller
class MainController (
    private val sectionRepository: SectionRepository,
) {

    @GetMapping("/")
    fun main(model: Model): String {
        val featuresSection = sectionRepository
            .findByPageAndSection("main", "features")
            ?.typedData<MainFeaturesSectionData>()

        val aboutSection = sectionRepository
            .findByPageAndSection("main", "about")
            ?.typedData<MainAboutSectionData>()

        val clmtSection = sectionRepository
            .findByPageAndSection("main", "clmt")
            ?.typedData<MainClmtSectionData>()

        val requirementsSection = sectionRepository
            .findByPageAndSection("main", "requirements")
            ?.typedData<MainRequirementsSectionData>()

        val ctaSection = sectionRepository
            .findByPageAndSection("main", "cta")
            ?.typedData<MainCtaSectionData>()

        with(model) {
            addAttribute("features", featuresSection)
            addAttribute("about", aboutSection)
            addAttribute("clmt", clmtSection)
            addAttribute("requirements", requirementsSection)
            addAttribute("cta", ctaSection)
        }

        return "pages/main"
    }
}