package store.technocyberlab.cyberlabsite.presentation.controllers.scenario.rest.store

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

abstract class BaseStoreController {
    @Autowired
    private lateinit var request: HttpServletRequest

    @Value("\${API_SECRET}")
    private lateinit var apiSecret: String

    protected fun validateApiKey() {
        val key = request.getHeader("X-Api-Key-Store")
        if (key != apiSecret) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API key")
        }
    }
}
