package store.technocyberlab.cyberlabsite.presentation.controllers.scenario.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioExecutionService
import store.technocyberlab.cyberlabsite.presentation.requests.scenario.*
import store.technocyberlab.cyberlabsite.presentation.responses.scenario.*
import java.util.UUID

@RestController
@RequestMapping("/api/scenario")
class ScenarioExecutionRestController(
    private val service: ScenarioExecutionService
) {
        @GetMapping("/started")
    fun isScenarioStarted(
        @RequestParam("scenario-id") scenarioId: UUID,
        @CookieValue("SESSION_ID") sessionId: UUID
    ): ResponseEntity<Boolean> {
        val dto = ScenarioExecutionService.DTO(scenarioId, sessionId)
        val started = service.isScenarioStarted(dto)
        return ResponseEntity.ok(started)
    }

    @PostMapping("/start")
    fun startScenario(
        @RequestBody request: StartScenarioRequest,
        @CookieValue("SESSION_ID") sessionId: UUID
    ): ResponseEntity<StartScenarioResponse> {
        val dto = ScenarioExecutionService.DTO(request.scenarioId, sessionId)
        return when (val result = service.startScenario(dto)) {
            is ScenarioExecutionService.StartResult.Next -> ResponseEntity
                .ok(StartScenarioResponse(step = result.step))

            is ScenarioExecutionService.StartResult.ScenarioNotFound -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(StartScenarioResponse(null))
        }
    }

    @PostMapping("/continue")
    fun continueScenario(
        @RequestBody request: ContinueScenarioRequest,
        @CookieValue("SESSION_ID") sessionId: UUID
    ): ResponseEntity<ContinueScenarioResponse> {
        val dto = ScenarioExecutionService.DTO(request.scenarioId, sessionId)
        return when (val result = service.continueScenario(dto)) {
            is ScenarioExecutionService.ContinueResult.Next -> ResponseEntity
                .ok(ContinueScenarioResponse(step = result.step))

            is ScenarioExecutionService.ContinueResult.InvalidSession -> ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ContinueScenarioResponse(null))

            is ScenarioExecutionService.ContinueResult.ScenarioNotFound -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ContinueScenarioResponse(null))
        }
    }

    @PostMapping("/advance")
    fun advanceToNextStep(
        @RequestBody request: AdvanceScenarioRequest,
        @CookieValue("SESSION_ID") sessionId: UUID
    ): ResponseEntity<AdvanceScenarioResponse> {
        val dto = ScenarioExecutionService.DTO(request.scenarioId, sessionId)
        return when (val result = service.advanceToNextStep(dto, request.flag)) {
            is ScenarioExecutionService.AdvanceResult.Next -> ResponseEntity
                .ok(AdvanceScenarioResponse(step = result.step))

            is ScenarioExecutionService.AdvanceResult.InvalidSession -> ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(AdvanceScenarioResponse(null, "InvalidSession"))

            is ScenarioExecutionService.AdvanceResult.InvalidFlag -> ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(AdvanceScenarioResponse(null, "InvalidFlag"))

            is ScenarioExecutionService.AdvanceResult.ScenarioNotFound -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(AdvanceScenarioResponse(null, "ScenarioNotFound"))

            is ScenarioExecutionService.AdvanceResult.Completed -> ResponseEntity
                .ok(AdvanceScenarioResponse(null, "Completed"))
        }
    }
}
