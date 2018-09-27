package pl.melements.gravebrowser.graveyard

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class GraveyardNotFoundException(
        reason: String? = null,
        throwable: Throwable? = null
) : ResponseStatusException(HttpStatus.BAD_REQUEST, reason, throwable)
