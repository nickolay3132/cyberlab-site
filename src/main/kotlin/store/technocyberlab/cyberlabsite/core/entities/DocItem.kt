package store.technocyberlab.cyberlabsite.core.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "doc_items")
data class DocItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val question: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val answer: String,

    @Column(nullable = false, name = "is_active")
    val isActive: Boolean = true,
)
