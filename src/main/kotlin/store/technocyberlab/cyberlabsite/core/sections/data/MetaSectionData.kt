package store.technocyberlab.cyberlabsite.core.sections.data

data class MetaSectionData(
    val title: String = "",
    val description: String = "",
    val keywords: String = "",
    var url: String = "",
) : SectionData
