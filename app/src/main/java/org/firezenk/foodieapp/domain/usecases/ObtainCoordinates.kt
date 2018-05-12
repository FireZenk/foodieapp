package org.firezenk.foodieapp.domain.usecases

import io.reactivex.Flowable
import org.firezenk.foodieapp.domain.models.Coordinates
import org.firezenk.foodieapp.domain.repositories.CoordinatesRepository
import javax.inject.Inject

class ObtainCoordinates @Inject constructor(private val repository: CoordinatesRepository) {

    fun execute(): Flowable<Coordinates> = repository.subscribeForCoordinates()
}