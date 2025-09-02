package store.technocyberlab.cyberlabsite.presentation.requests.scenario

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class ContinueScenarioRequest(
    @JsonProperty("scenario_id") val scenarioId: UUID,
)
