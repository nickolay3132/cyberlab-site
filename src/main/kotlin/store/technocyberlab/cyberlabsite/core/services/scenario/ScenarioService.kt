package store.technocyberlab.cyberlabsite.core.services.scenario

import java.util.UUID

interface ScenarioService {
    fun createSession(setSessionId: (sessionId: UUID) -> UUID): UUID
    fun getScenario(sessionId: UUID, scenarioId: UUID): Map<String, Any>
}