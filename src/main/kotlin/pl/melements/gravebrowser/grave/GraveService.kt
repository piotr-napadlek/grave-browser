package pl.melements.gravebrowser.grave

import pl.melements.gravebrowser.poznanapi.PoznanApi

class GraveService(val poznanApi: PoznanApi) {

    fun getGrave(id: Long) = poznanApi.getGrave(id)
}
