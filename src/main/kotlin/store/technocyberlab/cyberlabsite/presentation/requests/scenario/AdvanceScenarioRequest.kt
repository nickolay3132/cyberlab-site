package store.technocyberlab.cyberlabsite.presentation.requests.scenario

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class AdvanceScenarioRequest(
    @JsonProperty("scenario_id")
    val scenarioId: UUID,

    val flag: String,
)
