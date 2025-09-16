package store.technocyberlab.cyberlabsite.infrastructure.services.scenario

import org.springframework.stereotype.Service
import store.technocyberlab.cyberlabsite.core.entities.scenario.AttackType
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.enums.scenario.ScenarioDifficulty
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioSearchService
import store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.ScenarioDAO
import store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.ScenarioStepDAO
import store.technocyberlab.cyberlabsite.infrastructure.specifications.ScenarioSpecs
import store.technocyberlab.cyberlabsite.infrastructure.specifications.dsl.specs

@Service
class ScenarioSearchServiceImpl(
    private val scenarioDao: ScenarioDAO,
    private val stepDas: ScenarioStepDAO
) : ScenarioSearchService {

    override fun getFiltered(
        title: String?,
        difficulty: String?,
        attackTypeLabel: String?
    ): List<Scenario> {
        val specs = specs {
            addIf(!title.isNullOrBlank()) {
                ScenarioSpecs.containsTitle(title!!)
            }

            addIf(isNonBlankAndNotAll(difficulty)) {
                val enum = ScenarioDifficulty.valueOf(difficulty!!.uppercase())
                ScenarioSpecs.difficultyEquals(enum)
            }

            addIf(isNonBlankAndNotAll(attackTypeLabel)) {
                val attackType = AttackType(label = attackTypeLabel!!)
                ScenarioSpecs.attackTypeEquals(attackType)
            }

            add {
                ScenarioSpecs.isActive()
            }
        }

        return scenarioDao.all(specs).filter { scenario ->
            stepDas.setScenario(scenario)
            stepDas.exists()
        }
    }

    private fun isNonBlankAndNotAll(value: String?): Boolean {
        return !value.isNullOrBlank() && !value.equals("all", ignoreCase = true)
    }
}