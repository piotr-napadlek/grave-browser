package pl.melements.gravebrowser.grave

import pl.melements.gravebrowser.graveyard.GraveyardEntity
import java.time.LocalDate

data class GraveEntity(
        val id: Long,
        val buriedDate: LocalDate,
        val birthDate: LocalDate,
        val buriedFirstName: String,
        val buriedSecondName: String,
        val graveyardInfo: GraveyardEntity
)

//interface GraveRepository : Crud<GraveEntity, Long>

