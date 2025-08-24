package store.technocyberlab.cyberlabsite.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import store.technocyberlab.cyberlabsite.core.repositories.SectionRepository
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainAboutSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainClmtSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainCtaSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainFeaturesSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainRequirementsSectionData
import store.technocyberlab.cyberlabsite.core.sections.render.page

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

        return page(model) {
            title = "Test"

            section {
                name = "features"
                template = "fragments/main/features"
                data = featuresSection
            }

            section {
                name = "about"
                template = "fragments/main/about"
                data = aboutSection
            }

            section {
                name = "clmt"
                template = "fragments/main/clmt"
                data = clmtSection
            }

            section {
                name = "requirements"
                template = "fragments/main/requirements"
                data = requirementsSection
            }

            section {
                name = "cta"
                template = "fragments/main/cta"
                data = ctaSection
            }
        }
    }
}