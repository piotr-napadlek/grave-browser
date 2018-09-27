package pl.melements.gravebrowser.graveyard

import pl.melements.gravebrowser.poznanapi.PoznanApi
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.ValidationException

class GraveyardService(
        private val poznanApi: PoznanApi,
        private val graveyardRepository: GraveyardRepository
) {
    fun getGraveyards(dbOnly: Boolean = false) = graveyardRepository.findAll()
            .mergeWith(if (dbOnly) Flux.empty() else poznanApi.getAllGraveyards())
            .distinct { it.id }
            .flatMap { graveyardRepository.save(it) }

    fun getGraveyardById(id: Long) = graveyardRepository.findById(id)
            .switchIfEmpty(poznanApi.getGraveyard(id).flatMap(graveyardRepository::save))

    fun deleteGraveyard(id: Long) = graveyardRepository.deleteById(id)

    fun deleteFromRepo() = graveyardRepository.deleteAll()

    fun updateGraveyard(id: Long, graveyardEntity: GraveyardEntity) = Mono.just(id)
            .flatMap { graveyardRepository.findById(id) }
            .switchIfEmpty(poznanApi.getGraveyard(id))
            .switchIfEmpty(Mono.error<GraveyardEntity>(GraveyardNotFoundException("Graveyard with ID $id was not found")))
            .filter { (id == graveyardEntity.id) and (it.id == graveyardEntity.id) }
            .switchIfEmpty(validationExceptionMono())
            .flatMap { graveyardRepository.save(graveyardEntity) }

    private fun validationExceptionMono() = Mono.error<GraveyardEntity>(ValidationException("ID does not match the entity"))

}
