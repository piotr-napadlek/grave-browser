package pl.melements.gravebrowser.grave

import org.springframework.util.MultiValueMap
import pl.melements.gravebrowser.graveyard.GraveyardType
import reactor.core.publisher.Flux

data class GraveQueryParams(
        val graveyardType: GraveyardType?,
        val graveyardName: String?,
        val firstName: String?,
        val lastName: String?
) {
    constructor(map: MultiValueMap<String, String>) : this(
            GraveyardType.from(map.getFirst("graveyard_type")),
            map.getFirst("graveyard_name"),
            map.getFirst("first_name"),
            map.getFirst("last_name")
    )

    fun getApiQueryString(graveyardIdResolver: (name: String?, type: GraveyardType?) -> Flux<String>): Flux<String> {
        return graveyardIdResolver(graveyardName, graveyardType)
                .map { "&queryable=cm_id,g_surname,g_name&g_surname=$lastName&g_name=$firstName&cm_id=$it" }
                .switchIfEmpty { Flux.just("&queryable=cm_id,g_surname,g_name&g_surname=$lastName&g_name=$firstName") }
    }
}
