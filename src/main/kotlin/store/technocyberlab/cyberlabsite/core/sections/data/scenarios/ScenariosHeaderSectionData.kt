package store.technocyberlab.cyberlabsite.core.sections.data.scenarios

import store.technocyberlab.cyberlabsite.core.sections.data.SectionData
import store.technocyberlab.cyberlabsite.core.sections.traits.HasSubtitle
import store.technocyberlab.cyberlabsite.core.sections.traits.HasTitle

data class ScenariosHeaderSectionData(
    override val title: String,
    override val subtitle: String,
): SectionData, HasTitle, HasSubtitle
