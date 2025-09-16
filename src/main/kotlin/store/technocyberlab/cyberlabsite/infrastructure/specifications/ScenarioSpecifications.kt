package store.technocyberlab.cyberlabsite.infrastructure.specifications

import org.springframework.data.jpa.domain.Specification
import store.technocyberlab.cyberlabsite.core.entities.scenario.AttackType
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.enums.scenario.ScenarioDifficulty

object ScenarioSpecs {

    fun containsTitle(
        title: String
    ): Specification<Scenario> = Specification {root, _, builder ->
        builder.like(
            builder.lower(root.get<String>("title")),
            "%${title.lowercase()}%"
        )
    }

    fun difficultyEquals(
        difficulty: ScenarioDifficulty
    ): Specification<Scenario> = Specification {root, _, builder ->
        builder.equal(
            root.get<ScenarioDifficulty>("difficulty"),
            difficulty
        )
    }

    fun attackTypeEquals(
        attackType: AttackType
    ): Specification<Scenario> = Specification {root, _ , builder ->
        val join = root.join<Scenario, AttackType>("attackTypes")
        builder.equal(
            builder.lower(join.get("label")),
            attackType.label.lowercase()
        )
    }

    fun isActive(): Specification<Scenario> = Specification {root, _, builder ->
        builder.isTrue(root.get<Boolean>("isActive"))
    }
}