package store.technocyberlab.cyberlabsite.presentation.controllers.scenario.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioSearchService

@RestController
@RequestMapping("/api/scenarios")
class ScenarioController(
    private val scenarioSearchService: ScenarioSearchService
) {

    @GetMapping
    fun getFilteredScenarios(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) difficulty: String?,
        @RequestParam(required = false, name = "attack_type_label") attackTypeLabel: String?
    ): ResponseEntity<List<Scenario>> {
        val scenarios = scenarioSearchService.getFiltered(title, difficulty, attackTypeLabel)
        return ResponseEntity.ok(scenarios)
    }
}