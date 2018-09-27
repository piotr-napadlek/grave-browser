package pl.melements.gravebrowser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraveBrowserApplication

fun main(args: Array<String>) {
    runApplication<GraveBrowserApplication>(*args) {
        addInitializers(
                applicationBeans()
        )
    }
}
