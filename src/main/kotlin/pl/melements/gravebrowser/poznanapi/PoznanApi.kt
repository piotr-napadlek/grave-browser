package pl.melements.gravebrowser.poznanapi

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import pl.melements.gravebrowser.graveyard.GraveyardEntity
import pl.melements.gravebrowser.graveyard.GraveyardType
import pl.melements.gravebrowser.log
import reactor.core.publisher.Mono
import java.time.LocalDate

class PoznanApi(
        private val poznanApiClient: WebClient,
        private val objectMapper: ObjectMapper
) {

    fun getAllGraveyards() = poznanApiClient
            .get()
            .uri("/cmentarze/all.json")
            .retrieve().bodyToMono<String>()
            .flatMapIterable(this::mapGraveyardString)

    fun getGraveyard(id: Long) = poznanApiClient
            .get().uri("/cmentarze/$id")
            .retrieve().bodyToMono<String>()
            .flatMapIterable(this::mapGraveyardString)
            .next()

    fun getGrave(id: Long) = poznanApiClient.get().uri("/groby/$id.json")
            .retrieve()
            .bodyToMono<String>()
            .filter { it.matches("An error occured".toRegex()) }
            .switchIfEmpty(Mono.error(GraveNotFoundException("Grave with id $id was not found!")))
            .map(this::mapGraveString)

    private fun mapGraveString(graveString: String?): GraveDO = objectMapper.readTree(graveString).get("features").map {
                val properties = it.get("properties")
                log.info(it.toString())
                GraveDO(
                        id = it.get("id").asLong(),
                        buriedDate = LocalDate.parse(properties.get("g_date_burial").asText()),
                        birthDate = LocalDate.parse(properties.get("g_date_birth").asText()),
                        buriedFirstName = properties.get("print_name").asText(),
                        buriedSecondName = properties.get("print_surname").asText(),
                        graveyardId = properties.get("cm_id").asLong()
                )
            }.first()

    private fun mapGraveyardString(cemetery: String) = objectMapper.readTree(cemetery).get("features").map {
        GraveyardEntity(
                id = it.get("id").asLong(),
                type = GraveyardType.from(it.get("properties").get("cm_type").asText().trim())
                        ?: GraveyardType.MUNICIPAL,
                name = it.get("properties").get("cm_name").asText()
        )
    }
}
