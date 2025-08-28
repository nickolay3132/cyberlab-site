package store.technocyberlab.cyberlabsite.core.entities.scenario

import com.fasterxml.jackson.annotation.JsonManagedReference
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

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean,
) {
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
        name = "scenario_attack_types",
        joinColumns = [JoinColumn(name = "scenario_id")],
        inverseJoinColumns = [JoinColumn(name = "attack_type_id")]
    )
    lateinit var attackTypes: MutableSet<AttackType>

    fun shortDescription(): String {
        return if (description.length > 15) {
            description.substring(0, 70).trim() + "..."
        } else {
            description
        }
    }

    fun getLowerDifficulty(): String = difficulty.name.lowercase()
    fun getCapitalizedDifficulty(): String = difficulty.name.lowercase().replaceFirstChar { it.uppercase() }
}
