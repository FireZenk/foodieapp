package org.firezenk.foodieapp.domain.usecases

import org.firezenk.foodieapp.domain.repositories.VenuesRepository
import javax.inject.Inject

class ObtainFullVenue @Inject constructor(private val repository: VenuesRepository) {

    fun execute(venueName: String) = repository.downloadFullVenue(venueName)
}