package store.technocyberlab.cyberlabsite.core.entities.scenario

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "scenario_progresses")
data class ScenarioProgress(
    @Id
    @JsonIgnore
    val id: UUID = UUID.randomUUID(),

    val sessionId: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id")
    val scenario: Scenario,

    var currentStep: Int = 1,

    val startedAt: Instant = Instant.now(),

    var completedAt: Instant? = null
)

