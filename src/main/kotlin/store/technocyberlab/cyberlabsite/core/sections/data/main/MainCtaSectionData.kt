package store.technocyberlab.cyberlabsite.core.sections.data.main

import com.fasterxml.jackson.annotation.JsonProperty
import store.technocyberlab.cyberlabsite.core.sections.data.SectionData
import store.technocyberlab.cyberlabsite.core.sections.traits.HasSubtitle
import store.technocyberlab.cyberlabsite.core.sections.traits.HasTitle

data class MainCtaSectionData (
    override val title: String,
    override val subtitle: String,
    @JsonProperty("download_label")
    val downloadLabel: String,
) : SectionData, HasTitle, HasSubtitle