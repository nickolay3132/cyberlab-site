package store.technocyberlab.cyberlabsite.core.repositories.scenario

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import store.technocyberlab.cyberlabsite.core.entities.scenario.AttackType

@Repository
interface AttackTypeRepository : JpaRepository<AttackType, Int> {
    @Query("SELECT a.label FROM AttackType a")
    fun findAllLabels(): List<String>

    fun findByLabel(label: String): AttackType?
}