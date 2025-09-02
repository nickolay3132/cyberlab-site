package store.technocyberlab.cyberlabsite.core.repositories.scenario

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import store.technocyberlab.cyberlabsite.core.entities.scenario.ScenarioProgress
import java.util.UUID

@Repository
interface ScenarioProgressRepository : JpaRepository<ScenarioProgress, UUID> {
    fun existsByScenarioAndSessionId(scenario: Scenario, sessionId: UUID): Boolean
    fun findByScenarioAndSessionId(scenario: Scenario, sessionId: UUID): ScenarioProgress?
}