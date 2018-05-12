package org.firezenk.foodieapp.domain.repositories

import io.reactivex.Flowable
import org.firezenk.foodieapp.data.framework.CoordinatesDataSource
import org.firezenk.foodieapp.domain.models.Coordinates
import org.firezenk.foodieapp.domain.subscribeOnIO
import javax.inject.Inject

class CoordinatesRepository @Inject constructor(private val dataSource: CoordinatesDataSource) {

    fun subscribeForCoordinates(): Flowable<Coordinates>
            = dataSource.subscribeForCoordinates().subscribeOnIO()
}