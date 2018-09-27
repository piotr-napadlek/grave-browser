package pl.melements.gravebrowser.poznanapi

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class GraveNotFoundException(
        reason: String
) : ResponseStatusException(HttpStatus.NOT_FOUND, reason)
