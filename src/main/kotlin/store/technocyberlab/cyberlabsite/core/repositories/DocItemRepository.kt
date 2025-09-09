package store.technocyberlab.cyberlabsite.core.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import store.technocyberlab.cyberlabsite.core.entities.DocItem

@Repository
interface DocItemRepository : JpaRepository<DocItem, Long> {
    fun findAllByIsActiveTrue(): List<DocItem>
}