package store.technocyberlab.cyberlabsite.core.entities

import jakarta.persistence.*
import store.technocyberlab.cyberlabsite.core.enums.ReleaseTag

@Entity
@Table(name = "releases")
data class Release(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true, length = 15)
    val version: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val tag: ReleaseTag,

    @Column(nullable = false)
    val date: String,

    @Column(nullable = false)
    val notes: String,

    @Column(nullable = false)
    val size: String,

    @Column(nullable = false)
    val link: String,
)
