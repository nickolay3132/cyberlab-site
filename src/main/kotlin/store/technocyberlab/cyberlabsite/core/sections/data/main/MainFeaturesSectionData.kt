package store.technocyberlab.cyberlabsite.core.sections.data.main

import com.fasterxml.jackson.annotation.JsonProperty
import store.technocyberlab.cyberlabsite.core.sections.data.SectionData
import store.technocyberlab.cyberlabsite.core.sections.traits.HasTitle

data class MainFeaturesSectionData(
    override val title: String,
    val items: List<Item>
) : SectionData, HasTitle {
    data class Item(
        @JsonProperty("icon_code")
        val iconCode: String,
        val title: String,
        val description: String
    )
}
