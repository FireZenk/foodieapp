package org.firezenk.foodieapp.data.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.disposables.Disposable
import org.firezenk.foodieapp.domain.models.Coordinates
import javax.inject.Inject

class CoordinatesDataSource @Inject constructor(activity: Activity) {

    companion object {
        const val UPDATE_INTERVAL_IN_MILLIS = 30_000L
        const val MIN_DISTANCE = 0f
    }

    private lateinit var broadcaster: FlowableEmitter<Coordinates>

    private val flowable = Flowable.create(
            FlowableOnSubscribe<Coordinates> { emitter -> broadcaster = emitter },
            BackpressureStrategy.BUFFER)

    private var locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val locationListener = object: LocationListener {
        override fun onLocationChanged(location: Location) {
            broadcaster.onNext(mapCoordinates(location))
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }

    @SuppressLint("MissingPermission")
    fun subscribeForCoordinates(): Flowable<Coordinates> {
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, UPDATE_INTERVAL_IN_MILLIS, MIN_DISTANCE, locationListener)

        flowable.doOnSubscribe {
            broadcaster.onNext(
                    mapCoordinates(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)))
        }

        return flowable
    }

    private fun mapCoordinates(location: Location)
            = Coordinates(location.latitude, location.longitude)
}