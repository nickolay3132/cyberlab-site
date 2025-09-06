package store.technocyberlab.cyberlabsite.infrastructure.services.scenario

import jakarta.persistence.EntityNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.technocyberlab.cyberlabsite.core.dtos.AddAttackTypeDTO
import store.technocyberlab.cyberlabsite.core.dtos.CreateScenarioDTO
import store.technocyberlab.cyberlabsite.core.dtos.UpdateOrAddStepDTO
import store.technocyberlab.cyberlabsite.core.entities.scenario.AttackType
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.entities.scenario.ScenarioStep
import store.technocyberlab.cyberlabsite.core.repositories.scenario.AttackTypeRepository
import store.technocyberlab.cyberlabsite.core.repositories.scenario.ScenarioProgressRepository
import store.technocyberlab.cyberlabsite.core.repositories.scenario.ScenarioRepository
import store.technocyberlab.cyberlabsite.core.repositories.scenario.ScenarioStepRepository
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioStoreService
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Service
class ScenarioStoreServiceImpl(
    private val scenarioRepo: ScenarioRepository,
    private val stepRepo: ScenarioStepRepository,
    private val attackTypeRepo: AttackTypeRepository,
    private val scenarioProgressRepo: ScenarioProgressRepository,
    private val encoder: BCryptPasswordEncoder = BCryptPasswordEncoder(),
) : ScenarioStoreService {

    @Transactional
    override fun createScenario(dto: CreateScenarioDTO): Scenario {
        val attackTypes = attackTypeRepo.findAll()
            .filter { dto.attackTypes.contains(it.label) }
            .toMutableSet()

        val scenario = Scenario(
            title = dto.title,
            description = dto.description,
            difficulty = dto.difficulty,
            isActive = dto.isActive
        ).apply { this.attackTypes = attackTypes}

        val savedScenario = scenarioRepo.save(scenario)

        val steps = dto.steps.map {
            ScenarioStep(
                scenario = savedScenario,
                stepIndex = it.stepIndex,
                instruction = it.instruction,
                expectedFlagHash = encoder.encode(it.expectedFlagHash)
            )
        }

        stepRepo.saveAll(steps)
        return savedScenario
    }

    override fun updateOrAddStep(
        scenarioId: UUID,
        stepDto: UpdateOrAddStepDTO
    ) {
        val scenario = scenarioRepo.findById(scenarioId).orElseThrow()
        val existing = stepRepo.findByScenarioIdAndStepIndex(scenarioId, stepDto.stepIndex)

        if (existing != null) {
            val updated = existing.copy(
                instruction = stepDto.instruction,
                expectedFlagHash = encoder.encode(stepDto.expectedFlagHash)
            )
            stepRepo.save(updated)
        } else {
            val newStep = ScenarioStep(
                scenario = scenario,
                stepIndex = stepDto.stepIndex,
                instruction = stepDto.instruction,
                expectedFlagHash = encoder.encode(stepDto.expectedFlagHash)
            )
            stepRepo.save(newStep)
        }
    }

    override fun addAttackType(scenarioId: UUID, label: String) {
        val attackType = attackTypeRepo.findByLabel(label)
            ?: throw EntityNotFoundException("Attack type not found")
        val scenario = scenarioRepo.findById(scenarioId).getOrNull()
            ?: throw EntityNotFoundException("Scenario not found")

        scenario.attackTypes.add(attackType)
        scenarioRepo.save(scenario)
    }

    override fun getScenarioByTitle(title: String): Scenario {
        return scenarioRepo.findAll().firstOrNull { it.title == title }
            ?: throw EntityNotFoundException("Scenario not found")
    }

    @Transactional
    override fun deleteScenario(scenarioId: UUID) {
        val scenario = scenarioRepo.findById(scenarioId).orElseThrow()
        val steps = stepRepo.findAll().filter { it.scenario.id == scenarioId }
        val progress = scenarioProgressRepo.findAllByScenario(scenario)
        scenarioProgressRepo.deleteAll(progress)
        stepRepo.deleteAll(steps)
        scenarioRepo.delete(scenario)
    }

    override fun createAttackType(dto: AddAttackTypeDTO): AttackType {
        if (attackTypeRepo.findAllLabels().contains(dto.label)) {
            throw IllegalArgumentException("AttackType with label '${dto.label}' already exists")
        }
        return attackTypeRepo.save(AttackType(label = dto.label, description = dto.description))
    }
}