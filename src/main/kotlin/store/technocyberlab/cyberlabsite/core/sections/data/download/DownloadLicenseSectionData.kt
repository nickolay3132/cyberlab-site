package store.technocyberlab.cyberlabsite.core.sections.data.download

import store.technocyberlab.cyberlabsite.core.sections.data.SectionData
import store.technocyberlab.cyberlabsite.core.sections.traits.HasTitle

data class DownloadLicenseSectionData(
    override val title: String,
    val text: String,
): SectionData, HasTitle
