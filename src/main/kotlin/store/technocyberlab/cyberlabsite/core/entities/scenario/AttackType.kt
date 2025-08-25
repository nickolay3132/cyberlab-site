package store.technocyberlab.cyberlabsite.core.entities.scenario

import jakarta.persistence.*

@Entity
@Table(name = "attack_types")
data class AttackType (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false)
    val label: String,

    @Column
    val description: String? = null,
)