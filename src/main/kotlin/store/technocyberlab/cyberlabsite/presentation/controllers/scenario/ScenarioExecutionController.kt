package store.technocyberlab.cyberlabsite.presentation.controllers.scenario

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/scenario")
class ScenarioExecutionController {
    @GetMapping("/{uuid}")
    fun getScenario(
        model: Model,
        @PathVariable uuid: String
    ): String {
        with(model) {
//            addAttribute("page", "scenarios")
        }
        return "pages/scenarios/scenario"
    }
}