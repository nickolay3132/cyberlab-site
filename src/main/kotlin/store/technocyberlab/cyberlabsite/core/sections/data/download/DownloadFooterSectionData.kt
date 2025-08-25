package store.technocyberlab.cyberlabsite.core.sections.data.download

import com.fasterxml.jackson.annotation.JsonProperty
import store.technocyberlab.cyberlabsite.core.sections.data.SectionData

data class DownloadFooterSectionData(
    val hint: String,
    val link: String,
    @JsonProperty("browse_label")
    val browseLabel: String,
): SectionData
