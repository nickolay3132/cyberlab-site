package store.technocyberlab.cyberlabsite.core.sections.main

import com.fasterxml.jackson.annotation.JsonProperty
import store.technocyberlab.cyberlabsite.core.sections.SectionData

data class MainFeaturesSectionData(
    val title: String,
    val items: List<Item>
) : SectionData {
    data class Item(
        @JsonProperty("icon_code")
        val iconCode: String,
        val title: String,
        val description: String
    )
}
