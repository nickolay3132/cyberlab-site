package store.technocyberlab.cyberlabsite.infrastructure.services.scenario

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.technocyberlab.cyberlabsite.core.entities.scenario.ScenarioProgress
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioExecutionService
import store.technocyberlab.cyberlabsite.infrastructure.context.loaders.scenarios.ScenarioContextLoader
import java.time.Instant
import java.util.*

@Service
class ScenarioExecutionServiceImpl(
    private val context: ScenarioContextLoader
) : ScenarioExecutionService {
   override fun isScenarioStarted(dto: ScenarioExecutionService.DTO): Boolean {
        val scenario = context.loadScenario(dto.scenarioId) ?: return false
        return context.progressExists(scenario, dto.sessionId)
    }

    @Transactional
    override fun startScenario(dto: ScenarioExecutionService.DTO): ScenarioExecutionService.StartResult {
        // PREPARE DATA
        val scenario = context.loadScenario(dto.scenarioId)
            ?: return ScenarioExecutionService.StartResult.ScenarioNotFound

        val scenarioProgress = ScenarioProgress(sessionId = dto.sessionId, scenario = scenario)

        val step = context.loadStep(scenario, scenarioProgress.currentStep)
            ?: return ScenarioExecutionService.StartResult.ScenarioNotFound
        // END PREPARE DATA


        // LOGIC
        val existingProgress = context.loadProgress(scenario, dto.sessionId)
        if (existingProgress != null) {
            context.deleteProgress(existingProgress)
        }

        context.saveProgress(scenarioProgress)
        // END LOGIC

        return ScenarioExecutionService.StartResult.Next(step)
    }

    override fun continueScenario(dto: ScenarioExecutionService.DTO): ScenarioExecutionService.ContinueResult {
        // PREPARE DATA
        val scenario = context.loadScenario(dto.scenarioId)
            ?: return ScenarioExecutionService.ContinueResult.ScenarioNotFound

        val scenarioProgress = context.loadProgress(scenario, dto.sessionId)
            ?: return ScenarioExecutionService.ContinueResult.InvalidSession

        val step = context.loadStep(scenario, scenarioProgress.currentStep)
            ?: return ScenarioExecutionService.ContinueResult.ScenarioNotFound
        // END PREPARE DATA

        return ScenarioExecutionService.ContinueResult.Next(step)
    }

    @Transactional
    override fun advanceToNextStep(
        dto: ScenarioExecutionService.DTO,
        flag: String
    ): ScenarioExecutionService.AdvanceResult {
        // PREPARE DATA
        val scenario = context.loadScenario(dto.scenarioId)
            ?: return ScenarioExecutionService.AdvanceResult.ScenarioNotFound

        val scenarioProgress = context.loadProgress(scenario, dto.sessionId)
            ?: return ScenarioExecutionService.AdvanceResult.InvalidSession

        val step = context.loadStep(scenario, scenarioProgress.currentStep)
            ?: return ScenarioExecutionService.AdvanceResult.InvalidFlag

        val nextStepExists = context.stepExists(scenario, step.stepIndex + 1)

        val encoder = BCryptPasswordEncoder()
        // END PREPARE DATA


        // LOGIC
        if (!encoder.matches(flag, step.expectedFlagHash)) {
            return ScenarioExecutionService.AdvanceResult.InvalidFlag
        }

        if (!nextStepExists) {
            scenarioProgress.completedAt = Instant.now()
            return ScenarioExecutionService.AdvanceResult.Completed
        }

        scenarioProgress.currentStep += 1
        context.saveProgress(scenarioProgress)

        val nextStep = context.loadStep(scenario, scenarioProgress.currentStep)
            ?: return ScenarioExecutionService.AdvanceResult.ScenarioNotFound
        // END LOGIC

        return ScenarioExecutionService.AdvanceResult.Next(nextStep)
    }
}