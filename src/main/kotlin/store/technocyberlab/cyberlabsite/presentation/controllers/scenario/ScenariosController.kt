package store.technocyberlab.cyberlabsite.presentation.controllers.scenario

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ScenariosController {
    @GetMapping("/scenarios")
    fun scenarios(model: Model): String {
        return "pages/scenarios/list"
    }
}