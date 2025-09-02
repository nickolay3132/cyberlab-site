package store.technocyberlab.cyberlabsite.infrastructure.services.scenario

import org.springframework.stereotype.Service
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioService
import store.technocyberlab.cyberlabsite.infrastructure.context.loaders.scenarios.ScenarioContextLoader
import java.util.UUID

@Service
class ScenarioServiceImpl(
    private val context: ScenarioContextLoader,
) : ScenarioService {

    override fun createSession(setSessionId: (sessionId: UUID) -> UUID): UUID {
        val sessionId = UUID.randomUUID()
        return setSessionId(sessionId)
    }

    override fun getScenario(sessionId: UUID, scenarioId: UUID): Map<String, Any> {
        val scenario = context.loadScenario(scenarioId)!!
        val totalSteps = context.countSteps(scenarioId)
        val progress = context.loadProgress(scenario, sessionId)

        var currentStep = 0
        var scenarioIsStarted = false
        if (progress != null) {
            currentStep = progress.currentStep
            scenarioIsStarted = true
        }

        return mapOf(
            "scenario" to scenario,
            "totalSteps" to totalSteps,
            "currentStep" to currentStep,
            "scenarioIsStarted" to scenarioIsStarted
        )
    }
}