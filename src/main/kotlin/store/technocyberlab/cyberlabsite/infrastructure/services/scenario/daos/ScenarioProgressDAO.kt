package store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos

import org.springframework.stereotype.Component
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.entities.scenario.ScenarioProgress
import store.technocyberlab.cyberlabsite.core.exceptions.scenarios.ScenarioProgressNotFound
import store.technocyberlab.cyberlabsite.core.exceptions.scenarios.ScenarioRequiredException
import store.technocyberlab.cyberlabsite.core.exceptions.scenarios.SessionRequiredException
import store.technocyberlab.cyberlabsite.core.repositories.scenario.ScenarioProgressRepository
import store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.contracts.ScenarioAware
import store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.contracts.SessionAware
import java.util.UUID

@Component
class ScenarioProgressDAO(
    private val progressRepo: ScenarioProgressRepository,
    private val hasScenario: ScenarioAware,
    private val hasSession: SessionAware,
) {
    fun setScenario(scenario: Scenario) = hasScenario.setScenario(scenario)
    private fun getScenario() = hasScenario.getScenario()

    fun setSession(session: UUID) = hasSession.setSession(session)
    private fun getSession() = hasSession.getSession()

    @Throws(
        SessionRequiredException::class,
        ScenarioRequiredException::class,
    )
    fun getAll(): List<ScenarioProgress> {
        return progressRepo.findAllByScenario(getScenario())
    }

    @Throws(
        SessionRequiredException::class,
        ScenarioRequiredException::class,
        ScenarioProgressNotFound::class
    )
    fun get(
        filter: ((Scenario, ScenarioProgress) -> Boolean)? = null,
    ): ScenarioProgress {
        val scenario = getScenario()

        val found = progressRepo.findByScenarioAndSessionId(scenario, getSession())
            ?: throw ScenarioProgressNotFound("Progress for scenario ${scenario.id} for the active session")

        if (filter != null && !filter(scenario, found)) {
            throw ScenarioProgressNotFound("Progress for scenario ${scenario.id} for the active session did not pass filter")
        }

        return found
    }

    fun exists(
        scenario: Scenario
    ): Boolean {
        return progressRepo.existsByScenarioAndSessionId(scenario, getSession())
    }

    fun save(
        progress: ScenarioProgress,
    ): ScenarioProgress {
        return progressRepo.save(progress)
    }

    fun deleteAll(
        progresses: List<ScenarioProgress>,
    ) {
        progresses.forEach { delete(it) }
    }

    fun delete(
        progress: ScenarioProgress,
    ) {
      progressRepo.delete(progress)
    }
}