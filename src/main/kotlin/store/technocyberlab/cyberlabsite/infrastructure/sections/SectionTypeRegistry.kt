package store.technocyberlab.cyberlabsite.infrastructure.sections

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import store.technocyberlab.cyberlabsite.core.sections.data.MetaSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.SectionData
import store.technocyberlab.cyberlabsite.core.sections.data.download.DownloadFooterSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.download.DownloadHeaderSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.download.DownloadLicenseSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainAboutSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainClmtSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainCtaSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainFeaturesSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.main.MainRequirementsSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.scenarios.ScenariosHeaderSectionData

object SectionTypeRegistry {
    private val objectMapper = jacksonObjectMapper()
        .registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private val registry: Map<String, Class<out SectionData>> = mapOf(
        "main:meta" to MetaSectionData::class.java,
        "download:meta" to MetaSectionData::class.java,
        "scenarios:meta" to MetaSectionData::class.java,

        "main:features" to MainFeaturesSectionData::class.java,
        "main:about" to MainAboutSectionData::class.java,
        "main:clmt" to MainClmtSectionData::class.java,
        "main:requirements" to MainRequirementsSectionData::class.java,
        "main:cta" to MainCtaSectionData::class.java,

        "download:header" to DownloadHeaderSectionData::class.java,
        "download:footer" to DownloadFooterSectionData::class.java,
        "download:license" to DownloadLicenseSectionData::class.java,

        "scenarios:header" to ScenariosHeaderSectionData::class.java,
    )

    fun deserialize(key: String, raw: Map<String, Any>): SectionData? {
        val clazz = registry[key] ?: return null
        val json = objectMapper.writeValueAsString(raw)
        return runCatching { objectMapper.readValue(json, clazz) }.getOrNull()
    }
}