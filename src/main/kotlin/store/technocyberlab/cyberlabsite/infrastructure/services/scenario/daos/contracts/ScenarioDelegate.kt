package store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.contracts

import org.springframework.stereotype.Component
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.exceptions.scenarios.ScenarioRequiredException

@Component
class ScenarioDelegate : ScenarioAware {
    var scenarioInternal: Scenario? = null

    override fun setScenario(scenario: Scenario) {
        scenarioInternal = scenario
    }

    override fun getScenario(): Scenario {
        return scenarioInternal ?: throw ScenarioRequiredException("Scenario is missing")
    }
}