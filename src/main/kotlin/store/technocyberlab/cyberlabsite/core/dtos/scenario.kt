package store.technocyberlab.cyberlabsite.core.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import store.technocyberlab.cyberlabsite.core.enums.scenario.ScenarioDifficulty

data class CreateScenarioDTO(
    val title: String,
    val description: String,
    val difficulty: ScenarioDifficulty,
    @field:JsonProperty("is_active")
    val isActive: Boolean,
    @field:JsonProperty("attack_types")
    val attackTypes: List<String>,
    val steps: List<UpdateOrAddStepDTO>
)

data class UpdateOrAddStepDTO(
    @field:JsonProperty("step_index")
    val stepIndex: Int,
    val instruction: String,
    @field:JsonProperty("expected_flag_hash")
    val expectedFlagHash: String
)

data class AddAttackTypeDTO(
    val label: String,
    val description: String?
)
