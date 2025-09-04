package store.technocyberlab.cyberlabsite.presentation.controllers.scenario

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import store.technocyberlab.cyberlabsite.core.services.scenario.ScenarioService
import java.util.UUID

@Controller
@RequestMapping("/scenario")
class ScenarioExecutionController(
    private val scenarioService: ScenarioService
) {
    @GetMapping("/{uuid}")
    fun getScenario(
        request: HttpServletRequest,
        response: HttpServletResponse,
        model: Model,
        @PathVariable uuid: String
    ): String {
        val scenarioId = UUID.fromString(uuid)
        val sessionId: UUID = request.cookies
            ?.firstOrNull { it.name == "SESSION_ID" }
            ?.value
            ?.let { runCatching { UUID.fromString(it) }.getOrNull() }
            ?: scenarioService.createSession {sessionId ->
                val cookie = Cookie("SESSION_ID", sessionId.toString()).apply {
                    isHttpOnly = true
                    secure = true
                    path = "/"
                }
                response.addCookie(cookie)
                sessionId
            }

        val scenarioExtendedInfo = scenarioService.getScenario(sessionId, scenarioId)

        with(model) {
            addAttribute("page", "scenario")
            addAttribute("scenario", scenarioExtendedInfo["scenario"])
            addAttribute("totalSteps", scenarioExtendedInfo["totalSteps"])
            addAttribute("currentStep", scenarioExtendedInfo["currentStep"])
            addAttribute("scenarioIsStarted",  scenarioExtendedInfo["scenarioIsStarted"])
            addAttribute("scenarioIsCompleted",  scenarioExtendedInfo["scenarioIsCompleted"])
        }

        return "pages/scenarios/scenario"
    }
}