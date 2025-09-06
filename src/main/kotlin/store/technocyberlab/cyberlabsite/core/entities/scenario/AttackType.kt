package store.technocyberlab.cyberlabsite.core.entities.scenario

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "attack_types")
data class AttackType (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore
    var id: Int? = null,

    @Column(nullable = false, unique = true)
    val label: String,

    @Column
    val description: String? = null,

    @ManyToMany(mappedBy = "attackTypes")
    @JsonBackReference
    val scenarios: MutableSet<Scenario> = mutableSetOf()
)