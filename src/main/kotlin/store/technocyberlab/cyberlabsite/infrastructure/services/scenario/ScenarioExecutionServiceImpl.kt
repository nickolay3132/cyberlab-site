package store.technocyberlab.cyberlabsite.infrastructure.services.scenario

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.entities.scenario.ScenarioProgress
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioExecutionService
import store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.ScenarioDAO
import store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.ScenarioProgressDAO
import store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.ScenarioStepDAO
import java.time.Instant
import java.util.*

@Service
class ScenarioExecutionServiceImpl(
    private val scenarioDao: ScenarioDAO,
    private val stepDao: ScenarioStepDAO,
    private val progressDao: ScenarioProgressDAO,
) : ScenarioExecutionService {

    private fun prepareContext(
        scenarioId: UUID,
        sessionId: UUID
    ): Scenario? = runCatching {
        val scenario = scenarioDao.get(scenarioId, filter = { it.isActive })

        stepDao.setScenario(scenario)
        progressDao.setScenario(scenario)
        progressDao.setSession(sessionId)

        scenario
    }.getOrNull()

    override fun isScenarioStarted(
        dto: ScenarioExecutionService.DTO
    ): Boolean {
        return runCatching {
            progressDao.setScenario(scenarioDao.get(dto.scenarioId))
            progressDao.exists()
        }.getOrNull() ?: false
    }

    @Transactional
    override fun startScenario(
        dto: ScenarioExecutionService.DTO
    ): ScenarioExecutionService.StartResult {
        val scenario = prepareContext(dto.scenarioId, dto.sessionId)
            ?: return ScenarioExecutionService.StartResult.ScenarioNotFound

        val progress = ScenarioProgress(
            sessionId = dto.sessionId,
            scenario = scenario,
        )
        progressDao.getOrNull()?.let { progressDao.delete(it) }
        progressDao.save(progress)

        val step = stepDao.get(progress.currentStep)

        return ScenarioExecutionService.StartResult.Next(step)
    }

    override fun continueScenario(
        dto: ScenarioExecutionService.DTO
    ): ScenarioExecutionService.ContinueResult {
        prepareContext(dto.scenarioId, dto.sessionId)
            ?: return ScenarioExecutionService.ContinueResult.ScenarioNotFound

        val step = runCatching {
            stepDao.get(progressDao.get().currentStep)
        }.getOrNull() ?: return ScenarioExecutionService.ContinueResult.InvalidSession

        return ScenarioExecutionService.ContinueResult.Next(step)
    }

    @Transactional
    override fun advanceToNextStep(
        dto: ScenarioExecutionService.DTO,
        flag: String
    ): ScenarioExecutionService.AdvanceResult {
        prepareContext(dto.scenarioId, dto.sessionId)
            ?: return ScenarioExecutionService.AdvanceResult.ScenarioNotFound

        val progress = runCatching { progressDao.get() }.getOrNull() ?: return ScenarioExecutionService.AdvanceResult.InvalidSession
        val currentStep = stepDao.get(progress.currentStep)

        if (!BCryptPasswordEncoder().matches(flag, currentStep.expectedFlagHash)) {
            return ScenarioExecutionService.AdvanceResult.InvalidFlag
        }

        progress.currentStep += 1

        if (!stepDao.exists(progress.currentStep)) {
            progress.completedAt = Instant.now()
            return ScenarioExecutionService.AdvanceResult.Completed
        }

        progressDao.save(progress)
        val nextStep = runCatching {
            stepDao.get(progress.currentStep)
        }.getOrNull() ?: return ScenarioExecutionService.AdvanceResult.ScenarioNotFound

        return ScenarioExecutionService.AdvanceResult.Next(nextStep)
    }
}