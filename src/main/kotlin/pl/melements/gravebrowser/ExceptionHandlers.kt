package pl.melements.gravebrowser

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException


private class GraveBrowserException(
        status: HttpStatus,
        val errors: List<GraveBrowserError>
) : ResponseStatusException(status)

private class GraveBrowserError (
        val errorType: String,
        val message: String
)
