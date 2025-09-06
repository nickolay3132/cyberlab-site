package store.technocyberlab.cyberlabsite.presentation.controllers.scenario.rest.store

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import store.technocyberlab.cyberlabsite.core.dtos.CreateScenarioDTO
import store.technocyberlab.cyberlabsite.core.dtos.UpdateOrAddStepDTO
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioStoreService
import java.util.UUID

@RestController
@RequestMapping("/api/store/scenario")
class ScenarioStoreRestController(
    private val service: ScenarioStoreService,
) : BaseStoreController() {

    @PostMapping
    fun createScenario(
        @RequestBody dto: CreateScenarioDTO,
    ): ResponseEntity<UUID> {
        validateApiKey()
        val scenario = service.createScenario(dto)
        return ResponseEntity.ok(scenario.id)
    }

    @PutMapping("/{id}/step")
    fun updateStep(
        @PathVariable id: UUID,
        @RequestBody stepDto: UpdateOrAddStepDTO,
    ): ResponseEntity<Void> {
        validateApiKey()
        service.updateOrAddStep(id, stepDto)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{id}/attack-type")
    fun addAttackType(
        @PathVariable id: UUID,
        @RequestParam("label", required = true) label: String,
    ): ResponseEntity<Void> {
        validateApiKey()
        service.addAttackType(id, label)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun deleteScenario(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        validateApiKey()
        service.deleteScenario(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-title/{title}")
    fun getScenario(@PathVariable title: String): ResponseEntity<Scenario> {
        validateApiKey()
        val scenario = service.getScenarioByTitle(title)
        return ResponseEntity.ok(scenario)
    }
}