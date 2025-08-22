package store.technocyberlab.cyberlabsite.core.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import store.technocyberlab.cyberlabsite.core.entities.Section

@Repository
interface SectionRepository : JpaRepository<Section, Long> {
    fun findByPageAndSection(page: String, sectionKey: String): Section?
}