package store.technocyberlab.cyberlabsite.presentation.controllers.scenario

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import store.technocyberlab.cyberlabsite.core.repositories.SectionRepository
import store.technocyberlab.cyberlabsite.core.repositories.scenario.AttackTypeRepository
import store.technocyberlab.cyberlabsite.core.sections.data.scenarios.ScenariosHeaderSectionData
import store.technocyberlab.cyberlabsite.infrastructure.entities.extensions.typedData

@Controller
class ScenariosController(
    private val sectionRepository: SectionRepository,
    private val attackTypeRepository: AttackTypeRepository
) {
    @GetMapping("/scenarios")
    fun scenarios(model: Model, map: MutableMap<Any?, Any?>): String {
        val header = sectionRepository
            .findByPageAndSection("scenarios", "header")
            ?.typedData<ScenariosHeaderSectionData>()

        val search = mapOf(
            "id" to "search-scenarios",
            "label" to "Scenario Name",
            "placeholder" to "\uD83D\uDD0D Search by scenario name..."
        )

        val difficultySelect = mapOf(
            "dropdownId" to "difficulty",
            "label" to "Difficulty",
            "options" to listOf(
                "easy",
                "medium",
                "hard"
            )
        )

        val attackSelect = mapOf(
            "dropdownId" to "attack",
            "label" to "Attack Type",
            "options" to attackTypeRepository.findAllLabels()
        )

        println(attackSelect)

        with(model) {
            addAttribute("header", header)
            addAttribute("search", search)
            addAttribute("difficultySelect", difficultySelect)
            addAttribute("attackSelect", attackSelect)
        }
        return "pages/scenarios/list"
    }
}