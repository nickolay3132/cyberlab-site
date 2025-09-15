package store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.contracts

import org.springframework.stereotype.Component
import store.technocyberlab.cyberlabsite.core.exceptions.scenarios.SessionRequiredException
import java.util.UUID

@Component
class SessionDelegate : SessionAware {
    var sessionInternal: UUID? = null

    override fun setSession(session: UUID) {
        sessionInternal = session
    }

    override fun getSession(): UUID {
        return sessionInternal ?: throw SessionRequiredException("Session is missing")
    }
}