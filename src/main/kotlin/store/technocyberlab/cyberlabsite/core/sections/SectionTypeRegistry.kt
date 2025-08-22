package store.technocyberlab.cyberlabsite.core.sections

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import store.technocyberlab.cyberlabsite.core.sections.main.MainAboutSectionData
import store.technocyberlab.cyberlabsite.core.sections.main.MainClmtSectionData
import store.technocyberlab.cyberlabsite.core.sections.main.MainCtaSectionData
import store.technocyberlab.cyberlabsite.core.sections.main.MainFeaturesSectionData
import store.technocyberlab.cyberlabsite.core.sections.main.MainRequirementsSectionData

object SectionTypeRegistry {
    private val objectMapper = jacksonObjectMapper()
        .registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private val registry: Map<String, Class<out SectionData>> = mapOf(
        "main:features" to MainFeaturesSectionData::class.java,
        "main:about" to MainAboutSectionData::class.java,
        "main:clmt" to MainClmtSectionData::class.java,
        "main:requirements" to MainRequirementsSectionData::class.java,
        "main:cta" to MainCtaSectionData::class.java,
    )

    fun deserialize(key: String, raw: Map<String, Any>): SectionData? {
        val clazz = registry[key] ?: return null
        val json = objectMapper.writeValueAsString(raw)
        return runCatching { objectMapper.readValue(json, clazz) }.getOrNull()
    }
}