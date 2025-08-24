package store.technocyberlab.cyberlabsite.page.dsl.entities

data class Page(
    val title: String,
    val header: String,
    val footer: String,
    val sections: List<PageSection>,
)
