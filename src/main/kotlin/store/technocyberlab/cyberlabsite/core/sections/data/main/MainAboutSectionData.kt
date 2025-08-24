package store.technocyberlab.cyberlabsite.core.sections.data.main

import com.fasterxml.jackson.annotation.JsonProperty
import store.technocyberlab.cyberlabsite.core.sections.data.SectionData
import store.technocyberlab.cyberlabsite.core.sections.traits.HasSubtitle
import store.technocyberlab.cyberlabsite.core.sections.traits.HasTitle

data class MainAboutSectionData (
    override val title: String,
    override val subtitle: String,
    val image: String,
    val items: List<Item>
): SectionData, HasTitle, HasSubtitle {
    data class Item (
        @JsonProperty("icon_code")
        val iconCode: String,
        val title: String,
    )
}