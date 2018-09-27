package pl.melements.gravebrowser.poznanapi

import java.time.LocalDate

data class GraveDO(val id: Long,
                   val buriedDate: LocalDate,
                   val birthDate: LocalDate,
                   val buriedFirstName: String,
                   val buriedSecondName: String,
                   val graveyardId: Long)
