package store.technocyberlab.cyberlabsite.infrastructure.specifications

import org.springframework.data.jpa.domain.Specification
import store.technocyberlab.cyberlabsite.core.entities.scenario.AttackType
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.enums.scenario.ScenarioDifficulty

object ScenarioSpecifications {

    fun difficultyEquals(difficulty: String?): Specification<Scenario>? {
        return if (difficulty.isNullOrBlank() || difficulty.equals("all", ignoreCase = true)) {
            null
        } else {
            Specification { root, _, cb ->
                cb.equal(root.get<ScenarioDifficulty>("difficulty"), ScenarioDifficulty.valueOf(difficulty.uppercase()))
            }
        }
    }

    fun hasAttackType(label: String?): Specification<Scenario>? {
        return if (label.isNullOrBlank() || label.equals("all", ignoreCase = true)) {
            null
        } else {
            Specification { root, _, cb ->
                val join = root.join<Scenario, AttackType>("attackTypes")
                cb.equal(cb.lower(join.get("label")), label.lowercase())
            }
        }
    }

    fun titleContains(title: String?): Specification<Scenario>? {
        return if (title.isNullOrBlank()) null else Specification { root, _, cb ->
            cb.like(cb.lower(root.get("title")), "%${title.lowercase()}%")
        }
    }
}