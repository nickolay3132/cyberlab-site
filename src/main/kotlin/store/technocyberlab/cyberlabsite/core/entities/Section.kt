package store.technocyberlab.cyberlabsite.core.entities

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import store.technocyberlab.cyberlabsite.core.sections.SectionData
import store.technocyberlab.cyberlabsite.core.sections.SectionTypeRegistry

@Entity
@Table(name = "sections")
data class Section(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val page: String,

    @Column(nullable = false)
    val section: String,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    val data: Map<String, Any> = emptyMap()
) {
    fun typedData(): SectionData? {
        val key = "$page:$section"
        return SectionTypeRegistry.deserialize(key, data)
    }
}