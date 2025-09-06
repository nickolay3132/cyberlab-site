package store.technocyberlab.cyberlabsite.core.dtos

import store.technocyberlab.cyberlabsite.core.enums.scenario.ScenarioDifficulty

data class CreateScenarioDTO(
    val title: String,
    val description: String,
    val difficulty: ScenarioDifficulty,
    val isActive: Boolean,
    val attackTypes: List<String>,
    val steps: List<UpdateOrAddStepDTO>
)

data class UpdateOrAddStepDTO(
    val stepIndex: Int,
    val instruction: String,
    val expectedFlagHash: String
)

data class AddAttackTypeDTO(
    val label: String,
    val description: String?
)
