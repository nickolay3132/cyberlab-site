package store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos

import org.springframework.stereotype.Component
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.entities.scenario.ScenarioStep
import store.technocyberlab.cyberlabsite.core.exceptions.scenarios.ScenarioStepNotFoundException
import store.technocyberlab.cyberlabsite.core.repositories.scenario.ScenarioStepRepository
import store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.contracts.ScenarioAware

@Component
class ScenarioStepDAO(
    private val stepsRepo: ScenarioStepRepository,
    private val hasScenario: ScenarioAware,
) {
    fun setScenario(scenario: Scenario) = hasScenario.setScenario(scenario)
    private fun getScenario() = hasScenario.getScenario()

    @Throws(ScenarioStepNotFoundException::class)
    fun getAll(): List<ScenarioStep> {
        return stepsRepo.findAllByScenario(getScenario())
    }

    @Throws(ScenarioStepNotFoundException::class)
    fun get(
        stepIndex: Int,
        filter: ((Scenario, ScenarioStep) -> Boolean)? = null,
    ): ScenarioStep {
        val scenario = getScenario()

        val found = stepsRepo.findByScenarioIdAndStepIndex(scenario.id, stepIndex)
            ?: throw ScenarioStepNotFoundException("Scenario with id ${scenario.id} not found")

        if (filter != null && !filter(scenario, found)) {
            throw ScenarioStepNotFoundException("Scenario with id ${found.id} did pass filter")
        }

        return found
    }

    fun exists(
        stepIndex: Int? = null,
    ): Boolean {
        val scenario = getScenario()

        return when(stepIndex) {
            null -> stepsRepo.existsByScenarioId(scenario.id)
            else -> stepsRepo.existsByScenarioIdAndStepIndex(scenario.id, stepIndex)
        }
    }

    fun saveAll(
        steps: List<ScenarioStep>
    ): List<ScenarioStep> {
        return steps.map { save(it) }
    }

    fun save(
        step: ScenarioStep,
    ): ScenarioStep {
        return stepsRepo.save(step)
    }

    fun deleteAll(
        steps: List<ScenarioStep>
    ) {
        steps.forEach { delete(it) }
    }

    fun delete(
        step: ScenarioStep
    ) {
        stepsRepo.delete(step)
    }

    fun count(): Long {
        return stepsRepo.countByScenarioId(getScenario().id)
    }
}