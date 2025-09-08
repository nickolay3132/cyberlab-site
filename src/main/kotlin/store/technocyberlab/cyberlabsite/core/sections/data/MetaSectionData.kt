package store.technocyberlab.cyberlabsite.core.sections.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class MetaSectionData(
    val title: String = "",
    val description: String = "",
    val keywords: String = "",
    var url: String = "",
) : SectionData
