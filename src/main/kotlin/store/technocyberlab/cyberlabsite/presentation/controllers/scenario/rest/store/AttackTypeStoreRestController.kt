package store.technocyberlab.cyberlabsite.presentation.controllers.scenario.rest.store

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.technocyberlab.cyberlabsite.core.dtos.AddAttackTypeDTO
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioStoreService

@RestController
@RequestMapping("/api/store/attack-type")
class AttackTypeStoreRestController(
    private val service: ScenarioStoreService
) : BaseStoreController() {

    @PostMapping
    fun addAttackType(
        @RequestBody dto: AddAttackTypeDTO,
    ): ResponseEntity<Int?> {
        validateApiKey()
        val type = service.createAttackType(dto)
        return ResponseEntity.ok(type.id)
    }
}