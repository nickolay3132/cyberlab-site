package store.technocyberlab.cyberlabsite.page.dsl.entities


data class PageSection(
    val name: String,
    val template: String,
    val fragment: String,
    val data: Any
)
