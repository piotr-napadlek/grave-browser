package pl.melements.gravebrowser.graveyard

import org.springframework.data.repository.reactive.ReactiveCrudRepository

enum class GraveyardType(
        private val desc: String
) {
    MUNICIPAL ("komunalny"),
    MILITARY ("wojskowy"),
    PARISH ("parafialny"),
    HISTORIC ("zabytkowy");

    companion object {
        fun from(desc: String?) = GraveyardType.values().find { it.desc == desc }
    }
}

class GraveyardEntity (
        val id: Long,
        val type: GraveyardType?,
        val name: String?
) {
    constructor(id: Long) : this(id, null, null)
}

interface GraveyardRepository : ReactiveCrudRepository<GraveyardEntity, Long>