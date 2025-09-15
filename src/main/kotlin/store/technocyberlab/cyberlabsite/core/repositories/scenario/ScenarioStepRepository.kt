package store.technocyberlab.cyberlabsite.core.repositories.scenario

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.entities.scenario.ScenarioStep
import java.util.UUID

@Repository
interface ScenarioStepRepository : JpaRepository<ScenarioStep, UUID> {
    fun findByScenarioIdAndStepIndex(scenarioId: UUID, stepIndex: Int): ScenarioStep?
    fun findAllByScenario(scenario: Scenario): List<ScenarioStep>
    fun existsByScenarioIdAndStepIndex(scenarioId: UUID, stepIndex: Int): Boolean
    fun existsByScenarioId(scenarioId: UUID): Boolean
    fun countByScenarioId(scenarioId: UUID): Long
}