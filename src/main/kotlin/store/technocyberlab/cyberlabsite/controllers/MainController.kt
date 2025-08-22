package store.technocyberlab.cyberlabsite.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import store.technocyberlab.cyberlabsite.core.repositories.SectionRepository
import store.technocyberlab.cyberlabsite.core.sections.main.MainAboutSectionData
import store.technocyberlab.cyberlabsite.core.sections.main.MainClmtSectionData
import store.technocyberlab.cyberlabsite.core.sections.main.MainCtaSectionData
import store.technocyberlab.cyberlabsite.core.sections.main.MainFeaturesSectionData
import store.technocyberlab.cyberlabsite.core.sections.main.MainRequirementsSectionData

@Controller
class MainController (
    private val sectionRepository: SectionRepository,
) {

    @GetMapping("/")
    fun main(model: Model): String {
        val featuresSection = sectionRepository
            .findByPageAndSection("main", "features")
            ?.typedData() as? MainFeaturesSectionData

        val aboutSection = sectionRepository
            .findByPageAndSection("main", "about")
            ?.typedData() as? MainAboutSectionData

        val clmtSection = sectionRepository
            .findByPageAndSection("main", "clmt")
            ?.typedData() as? MainClmtSectionData

        val requirementsSection = sectionRepository
            .findByPageAndSection("main", "requirements")
            ?.typedData() as? MainRequirementsSectionData

        val ctaSection = sectionRepository
            .findByPageAndSection("main", "cta")
            ?.typedData() as? MainCtaSectionData

        model.addAttribute("sections", mapOf(
            "features" to featuresSection,
            "about" to aboutSection,
            "clmt" to clmtSection,
            "requirements" to requirementsSection,
            "cta" to ctaSection
        ))
        return "pages/main"
    }
}