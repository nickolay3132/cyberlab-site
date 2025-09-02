package store.technocyberlab.cyberlabsite.infrastructure.context.loaders.scenarios

import org.springframework.stereotype.Service
import store.technocyberlab.cyberlabsite.core.entities.scenario.*
import store.technocyberlab.cyberlabsite.core.repositories.scenario.*
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Service
class ScenarioContextLoader(
    private val scenarioProgressRepository: ScenarioProgressRepository,
    private val scenarioRepository: ScenarioRepository,
    private val scenarioStepRepository: ScenarioStepRepository,
) {
    // LOAD
    fun loadScenario(scenarioId: UUID): Scenario? =
        scenarioRepository.findById(scenarioId).getOrNull()

    fun loadProgress(scenario: Scenario, sessionId: UUID): ScenarioProgress? =
        scenarioProgressRepository.findByScenarioAndSessionId(scenario, sessionId)

    fun loadStep(scenario: Scenario, stepIndex: Int): ScenarioStep? =
        scenarioStepRepository.findByScenarioIdAndStepIndex(scenario.id, stepIndex)
    // END LOAD

    // EXISTS
    fun progressExists(scenario: Scenario, sessionId: UUID): Boolean =
        scenarioProgressRepository.existsByScenarioAndSessionId(scenario, sessionId)

    fun stepExists(scenario: Scenario, stepIndex: Int): Boolean =
        scenarioStepRepository.existsByScenarioIdAndStepIndex(scenario.id, stepIndex)
    // END EXISTS

    // SAVE
    fun saveProgress(progress: ScenarioProgress) =
        scenarioProgressRepository.save(progress)
    // END SAVE

    // DELETE
    fun deleteProgress(progress: ScenarioProgress) =
            scenarioProgressRepository.delete(progress)
    // END DELETE

    // COUNT
    fun countSteps(scenarioId: UUID): Long =
        scenarioStepRepository.countByScenarioId(scenarioId)
    // END COUNT
}