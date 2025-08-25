package store.technocyberlab.cyberlabsite.core.entities.scenario

import jakarta.persistence.*
import store.technocyberlab.cyberlabsite.core.enums.scenario.ScenarioDifficulty
import java.util.UUID

@Entity
@Table(name = "scenarios")
data class Scenario(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val title: String,

    @Column(columnDefinition = "TEXT")
    val description: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val difficulty: ScenarioDifficulty,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attack_type_id")
    val attackType: AttackType,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean,
)
