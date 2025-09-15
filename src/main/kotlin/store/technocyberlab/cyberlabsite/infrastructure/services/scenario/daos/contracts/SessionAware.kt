package store.technocyberlab.cyberlabsite.infrastructure.services.scenario.daos.contracts

import store.technocyberlab.cyberlabsite.core.exceptions.scenarios.SessionRequiredException
import java.util.UUID
import kotlin.jvm.Throws

interface SessionAware {
    fun setSession(session: UUID)

    @Throws(SessionRequiredException::class)
    fun getSession(): UUID
}