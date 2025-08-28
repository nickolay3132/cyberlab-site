package store.technocyberlab.cyberlabsite.core.services.scenario

import store.technocyberlab.cyberlabsite.core.entities.scenario.Scenario

interface ScenarioSearchService {
    fun getFiltered(title: String?, difficulty: String?, attackTypeLabel: String?): List<Scenario>
}