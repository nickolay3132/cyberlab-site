package store.technocyberlab.cyberlabsite.core.services.scenario

import store.technocyberlab.cyberlabsite.core.entities.scenario.ScenarioStep
import java.util.UUID

interface ScenarioExecutionService {
    fun isScenarioStarted(dto: DTO): Boolean

    fun startScenario(dto: DTO): StartResult

    fun continueScenario(dto: DTO): ContinueResult

    fun advanceToNextStep(dto: DTO, flag: String): AdvanceResult

    data class DTO(
        val scenarioId: UUID,
        val sessionId: UUID,
    )

    sealed class StartResult {
        data class Next(val step: ScenarioStep): StartResult()
        object ScenarioNotFound: StartResult()
    }

    sealed class ContinueResult {
        data class Next(val step: ScenarioStep): ContinueResult()
        object InvalidSession : ContinueResult()
        object ScenarioNotFound: ContinueResult()
    }

    sealed class AdvanceResult {
        data class Next(val step: ScenarioStep): AdvanceResult()
        object InvalidSession : AdvanceResult()
        object InvalidFlag: AdvanceResult()
        object ScenarioNotFound: AdvanceResult()
        object Completed : AdvanceResult()
    }
}