package store.technocyberlab.cyberlabsite.core.sections.main

import com.fasterxml.jackson.annotation.JsonProperty
import store.technocyberlab.cyberlabsite.core.sections.SectionData

data class MainClmtSectionData(
    val title: String,
    val subtitle: String,
    @JsonProperty("clmt_features")
    val clmtFeatures: List<ClmtFeature>,
    @JsonProperty("code_samples")
    val codeSamples: List<CodeSample>,
) : SectionData {
    data class ClmtFeature(
        @JsonProperty("icon_code")
        val iconCode: String,
        val title: String,
        val description: String,
    )

    data class CodeSample(
        val title: String,
        val code: String,
    )
}
