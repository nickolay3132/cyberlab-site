package store.technocyberlab.cyberlabsite.presentation.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import store.technocyberlab.cyberlabsite.infrastructure.entities.extensions.typedData
import store.technocyberlab.cyberlabsite.core.repositories.ReleaseRepository
import store.technocyberlab.cyberlabsite.core.repositories.SectionRepository
import store.technocyberlab.cyberlabsite.core.sections.data.download.DownloadFooterSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.download.DownloadHeaderSectionData
import store.technocyberlab.cyberlabsite.core.sections.data.download.DownloadLicenseSectionData

@Controller
class DownloadController (
    private val sectionRepository: SectionRepository,
    private val releaseRepository: ReleaseRepository,
) {
    @GetMapping("/download")
    fun download(model: Model): String {
        val headerSection = sectionRepository
            .findByPageAndSection("download", "header")
            ?.typedData<DownloadHeaderSectionData>()

        val footerSection = sectionRepository
            .findByPageAndSection("download", "footer")
            ?.typedData<DownloadFooterSectionData>()

        val licenseSection = sectionRepository
            .findByPageAndSection("download", "license")
            ?.typedData<DownloadLicenseSectionData>()

        val releases = releaseRepository.findAll()


        with(model) {
            addAttribute("page", "download")

            addAttribute("header", headerSection)
            addAttribute("footer", footerSection)
            addAttribute("license", licenseSection)

            addAttribute("releases", releases)
        }

        return "pages/download"
    }
}