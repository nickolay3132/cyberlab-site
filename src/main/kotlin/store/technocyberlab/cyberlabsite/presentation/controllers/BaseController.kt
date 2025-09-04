package store.technocyberlab.cyberlabsite.presentation.controllers

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired

abstract class BaseController {
    @Autowired
    private lateinit var request: HttpServletRequest

    protected fun getUrl(): String {
        val uri = request.requestURI
        val normalizedUri = if (uri == "/") "" else uri
        return "${request.scheme}://${request.serverName}$normalizedUri"
    }
}