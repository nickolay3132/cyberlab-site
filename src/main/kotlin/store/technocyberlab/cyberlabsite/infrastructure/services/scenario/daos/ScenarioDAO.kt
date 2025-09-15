package store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos

import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.exceptions.scenarios.ScenarioNotFoundException
import store.technocyberlab.cyberlabsite.core.repositories.scenario.ScenarioRepository
import java.util.UUID

@Component
class ScenarioDAO(
    private val scenariosRepo: ScenarioRepository,
) {
    @Throws(ScenarioNotFoundException::class)
    fun get(
        scenarioId: UUID,
        filter: ((Scenario) -> Boolean)? = null
    ): Scenario {
        val found = scenariosRepo.findById(scenarioId).orElseThrow {
            ScenarioNotFoundException("Scenario with id $scenarioId not found")
        }

        if (filter != null && !filter(found)) {
            throw ScenarioNotFoundException("Scenario with id $scenarioId did not pass filter")
        }

        return found
    }

    fun all(
        spec: Specification<Scenario>? = null,
    ): List<Scenario> {
        return when (spec) {
            null -> scenariosRepo.findAll()
            else -> scenariosRepo.findAll(spec)
        }
    }

    fun save(scenario: Scenario): Scenario =
        scenariosRepo.save(scenario)

    fun delete(scenario: Scenario) =
        scenariosRepo.delete(scenario)


}