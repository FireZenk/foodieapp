package org.firezenk.foodieapp.data.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.*
import android.os.Bundle
import io.reactivex.Flowable
import org.firezenk.foodieapp.domain.models.Coordinates
import javax.inject.Inject
import io.reactivex.processors.PublishProcessor

class CoordinatesDataSource @Inject constructor(private val activity: Activity) {

    companion object {
        const val UPDATE_INTERVAL_IN_MILLIS = 10_000L
        const val MIN_DISTANCE = 0f
    }

    private val publisher: PublishProcessor<Coordinates> = PublishProcessor.create()
    private val flowable = publisher.onBackpressureLatest()

    private var locationManager: LocationManager
            = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val networkProvider: LocationProvider? by lazy {
        locationManager.getProvider(LocationManager.NETWORK_PROVIDER)
    }

    private val gpsProvider: LocationProvider by lazy {
        locationManager.getProvider(LocationManager.GPS_PROVIDER)
    }

    private val locationListener = object: LocationListener {
        override fun onLocationChanged(location: Location) {
            publisher.onNext(mapCoordinates(location))
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }

    @SuppressLint("MissingPermission")
    fun subscribeForCoordinates(): Flowable<Coordinates> {
        networkProvider?.let {
            locationManager.requestLocationUpdates(
                    it.name, UPDATE_INTERVAL_IN_MILLIS, MIN_DISTANCE, locationListener)
        }

        locationManager.requestLocationUpdates(
                gpsProvider.name, UPDATE_INTERVAL_IN_MILLIS, MIN_DISTANCE, locationListener)

        return flowable
    }

    private fun mapCoordinates(location: Location)
            = Coordinates(location.latitude, location.longitude)
}