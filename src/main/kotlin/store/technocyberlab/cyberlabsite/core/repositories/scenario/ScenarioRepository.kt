package store.technocyberlab.cyberlabsite.core.repositories.scenario

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario
import java.util.UUID

@Repository
interface ScenarioRepository : JpaRepository<Scenario, UUID>, JpaSpecificationExecutor<Scenario> {
    fun findAllByIsActiveTrue(): List<Scenario>
}