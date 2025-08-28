package store.technocyberlab.cyberlabsite.infrastructure.services.scenario

import org.springframework.stereotype.Service
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.repositories.scenario.ScenarioRepository
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioSearchService
import store.technocyberlab.cyberlabsite.infrastructure.specifications.ScenarioSpecifications

@Service
class ScenarioServiceImpl(
    private val scenarioRepository: ScenarioRepository,
) : ScenarioSearchService {
    override fun getFiltered(
        title: String?,
        difficulty: String?,
        attackTypeLabel: String?
    ): List<Scenario> {
        val specs = listOfNotNull(
            ScenarioSpecifications.titleContains(title),
            ScenarioSpecifications.hasAttackType(attackTypeLabel),
            ScenarioSpecifications.difficultyEquals(difficulty)
        )

        val combinedSpec = specs.reduceOrNull { acc, spec -> acc.and(spec) }

        return scenarioRepository.findAll(combinedSpec).toList()
    }
}