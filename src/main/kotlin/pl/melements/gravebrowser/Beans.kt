package pl.melements.gravebrowser

import org.springframework.context.support.beans
import org.springframework.core.env.Environment
import org.springframework.web.reactive.function.client.WebClient
import pl.melements.gravebrowser.grave.GraveService
import pl.melements.gravebrowser.graveyard.GraveyardService
import pl.melements.gravebrowser.poznanapi.PoznanApi

fun applicationBeans() = beans {
    bean<GraveService>()
    bean<GraveyardService>()
    bean<Handlers>()
    bean<PoznanApi>()
    bean(name = "poznanApiClient") {
        WebClient.builder().baseUrl(ref<Environment>().getProperty("poznan.api.url")!!).build()
    }
    bean { routes(ref()) }
}

