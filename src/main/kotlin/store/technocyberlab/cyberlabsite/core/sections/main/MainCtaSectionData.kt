package store.technocyberlab.cyberlabsite.core.sections.main

import com.fasterxml.jackson.annotation.JsonProperty
import store.technocyberlab.cyberlabsite.core.sections.SectionData

data class MainCtaSectionData (
    val title: String,
    val subtitle: String,
    @JsonProperty("download_label")
    val downloadLabel: String,
) : SectionData