package store.technocyberlab.cyberlabsite.core.sections.data.main

import com.fasterxml.jackson.annotation.JsonProperty
import store.technocyberlab.cyberlabsite.core.sections.data.SectionData
import store.technocyberlab.cyberlabsite.core.sections.traits.HasSubtitle
import store.technocyberlab.cyberlabsite.core.sections.traits.HasTitle

data class MainClmtSectionData(
    override val title: String,
    override val subtitle: String,
    @JsonProperty("clmt_features")
    val clmtFeatures: List<ClmtFeature>,
    @JsonProperty("code_samples")
    val codeSamples: List<CodeSample>,

) : SectionData, HasTitle, HasSubtitle {
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
