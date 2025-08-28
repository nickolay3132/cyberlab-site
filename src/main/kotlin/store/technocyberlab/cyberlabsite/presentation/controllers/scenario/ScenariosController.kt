package store.technocyberlab.cyberlabsite.presentation.controllers.scenario

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.repositories.SectionRepository
import store.technocyberlab.cyberlabsite.core.repositories.scenario.AttackTypeRepository
import store.technocyberlab.cyberlabsite.core.sections.data.scenarios.ScenariosHeaderSectionData
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioSearchService
import store.technocyberlab.cyberlabsite.infrastructure.entities.extensions.typedData

@Controller
class ScenariosController(
    private val sectionRepository: SectionRepository,
    private val attackTypeRepository: AttackTypeRepository,
    private val scenarioSearchService: ScenarioSearchService
) {
    @GetMapping("/scenarios")
    fun scenarios(
        model: Model,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) difficulty: String?,
        @RequestParam(required = false, name = "attack_type_label") attackTypeLabel: String?
    ): String {
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

        val scenarios: List<Scenario> = scenarioSearchService.getFiltered(title, difficulty, attackTypeLabel)

        with(model) {
            addAttribute("header", header)
            addAttribute("search", search)
            addAttribute("difficultySelect", difficultySelect)
            addAttribute("attackSelect", attackSelect)
            addAttribute("scenarios", scenarios)
        }
        return "pages/scenarios/list"
    }
}