package pl.melements.gravebrowser

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

fun routes(handlers: Handlers) = router {
    "/".and(accept(MediaType.APPLICATION_JSON_UTF8)).nest {
        "/graves".nest {
            GET("/{id:[0-9]+}", handlers::getGraveWithId)
            GET("/", handlers::findGraves)
        }
        "/graveyards".nest {
            GET("/", handlers::getGraveyards)
            PUT("/{id:[0-9]+}", handlers::updateGraveyard)
            GET("/{id:[0-9]+}", handlers::getGraveyardWithId)
            DELETE("/{id:[0-9]+}", handlers::deleteGraveyard)
            POST("/clear", handlers::clearGraveyards)
        }
    }
}
