package pl.melements.gravebrowser

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import pl.melements.gravebrowser.grave.GraveEntity
import pl.melements.gravebrowser.graveyard.GraveyardService
import pl.melements.gravebrowser.grave.GraveService
import pl.melements.gravebrowser.graveyard.GraveyardEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class Handlers(
        private val graveService: GraveService,
        private val graveyardService: GraveyardService
) {

    fun getGraveyards(serverRequest: ServerRequest) = okApplicationJsonResponse()
            .body(graveyardService.getGraveyards(
                    serverRequest.queryParam("db_only").map(String::toBoolean).orElse(false)
            ))

    fun getGraveWithId(serverRequest: ServerRequest) = okApplicationJsonResponse()
            .body(graveService.getGrave(serverRequest.pathVariable("id").toLong()))

    fun getGraveyardWithId(serverRequest: ServerRequest) = okApplicationJsonResponse()
            .body(graveyardService.getGraveyardById(serverRequest.pathVariable("id").toLong()))

    fun clearGraveyards(serverRequest: ServerRequest) = ServerResponse.accepted()
            .build(graveyardService.deleteFromRepo())

    fun deleteGraveyard(serverRequest: ServerRequest) = graveyardService.deleteGraveyard(serverRequest.pathVariable("id").toLong())
            .flatMap { ServerResponse.ok().build() }

    fun updateGraveyard(serverRequest: ServerRequest) = serverRequest.bodyToMono<GraveyardEntity>()
            .flatMap { okApplicationJsonResponse().body(graveyardService.updateGraveyard(serverRequest.pathVariable("id").toLong(), it)) }

    fun findGraves(serverRequest: ServerRequest) = ServerResponse.ok().build()

    private fun okApplicationJsonResponse() = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)

}
