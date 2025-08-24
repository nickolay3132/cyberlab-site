package store.technocyberlab.cyberlabsite.core.entities.extensions

import store.technocyberlab.cyberlabsite.core.entities.Section
import store.technocyberlab.cyberlabsite.core.sections.data.SectionData
import store.technocyberlab.cyberlabsite.core.sections.data.SectionTypeRegistry

inline fun <reified T: SectionData> Section.typedData(): T? {
    val key = "$page:$section"
    return SectionTypeRegistry.deserialize(key, data) as? T
}