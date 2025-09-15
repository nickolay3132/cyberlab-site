package store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.contracts

import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.exceptions.scenarios.ScenarioRequiredException
import kotlin.jvm.Throws

interface ScenarioAware {
    fun setScenario(scenario: Scenario)

    @Throws(ScenarioRequiredException::class)
    fun getScenario(): Scenario
}