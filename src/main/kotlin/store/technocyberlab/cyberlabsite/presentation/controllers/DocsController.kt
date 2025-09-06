package store.technocyberlab.cyberlabsite.presentation.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/docs")
class DocsController {
    @GetMapping
    fun index(model: Model): String {
        with(model) {
            addAttribute("page", "docs")
        }
        return "pages/docs"
    }
}