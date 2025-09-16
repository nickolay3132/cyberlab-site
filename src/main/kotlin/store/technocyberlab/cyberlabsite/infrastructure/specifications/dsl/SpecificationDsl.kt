package store.technocyberlab.cyberlabsite.infrastructure.specifications.dsl

import org.springframework.data.jpa.domain.Specification

fun <T> specs(block: SpecsBuilder<T>.() -> Unit): Specification<T>? {
    val builder = SpecsBuilder<T>()
    builder.block()
    return builder.build()
}

class SpecsBuilder<T> {
    private val specs = mutableListOf<Specification<T>>()

    fun addIf(condition: Boolean, specProvider: () -> Specification<T>) {
        if (condition) add(specProvider)
    }

    fun add(specProvider: () -> Specification<T>) {
        specs += specProvider()
    }

    fun build(): Specification<T>? =
        specs.takeIf { it.isNotEmpty() }?.reduce { acc, spec -> acc.and(spec) }
}
