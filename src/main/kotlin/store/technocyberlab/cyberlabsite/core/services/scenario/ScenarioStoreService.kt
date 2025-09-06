package store.technocyberlab.cyberlabsite.core.services.scenario

import store.technocyberlab.cyberlabsite.core.dtos.AddAttackTypeDTO
import store.technocyberlab.cyberlabsite.core.dtos.CreateScenarioDTO
import store.technocyberlab.cyberlabsite.core.dtos.UpdateOrAddStepDTO
import store.technocyberlab.cyberlabsite.core.entities.scenario.AttackType
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import java.util.UUID

interface ScenarioStoreService {
    fun createScenario(dto: CreateScenarioDTO): Scenario
    fun updateOrAddStep(scenarioId: UUID, stepDto: UpdateOrAddStepDTO)
    fun addAttackType(scenarioId: UUID, label: String)
    fun getScenarioByTitle(title: String): Scenario
    fun deleteScenario(scenarioId: UUID)
    fun createAttackType(dto: AddAttackTypeDTO): AttackType
}