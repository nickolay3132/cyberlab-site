package store.technocyberlab.cyberlabsite.core.entities.scenario

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "scenario_steps")
data class ScenarioStep(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id")
    val scenario: Scenario,

    @Column(name = "step_index", nullable = false)
    val stepIndex: Int,

    @Column(columnDefinition = "TEXT", nullable = false)
    val instruction: String,

    @Column(name = "expected_flag_hash")
    val expectedFlagHash: String,
)
