package org.firezenk.foodieapp.ui.features.map

import android.Manifest
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import kotlinx.android.synthetic.main.screen_map.*
import org.firezenk.foodieapp.App.Companion.component
import org.firezenk.foodieapp.R
import org.firezenk.foodieapp.ui.extensions.grantPermissions
import org.firezenk.foodieapp.ui.features.commons.Screen
import org.firezenk.foodieapp.ui.features.map.di.MapModule
import javax.inject.Inject
import com.mapbox.mapboxsdk.annotations.IconFactory
import org.firezenk.foodieapp.ui.extensions.setVectorIcon
import org.firezenk.foodieapp.ui.utils.getBitmapFromVectorDrawable

class MapScreen : AppCompatActivity(), Screen<States> {

    companion object {
        private const val MAP_ZOOM = 15.0
    }

    @Inject lateinit var presenter: MapPresenter
    @Inject lateinit var actions: MapActions

    private lateinit var mapboxMap: MapboxMap
    private val userMarker: MarkerOptions by lazy {
        MarkerOptions().apply { setVectorIcon(this@MapScreen, R.drawable.ic_user_pin) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_map)

        component add MapModule(this) inject this

        presenter init this

        initView(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        presenter.destroy()
        super.onDestroy()
    }

    override fun render(state: States) {
        when (state) {
            is PositionReady -> onPositionReady(state)
            is PointersReady -> onPointersReady(state)
            is ErrorMessage -> onErrorMessage(state)
        }
    }

    private fun initView(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            mapboxMap = it
            mapboxMap.setOnMarkerClickListener {
                Snackbar.make(mapView, it.title, Snackbar.LENGTH_SHORT).show()
                return@setOnMarkerClickListener true
            }
        }

        askForPermissions()
    }

    private fun askForPermissions() {
        grantPermissions(listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),
                { presenter reduce actions.loadVenues() })
    }

    private fun onPositionReady(state: PositionReady) {
        val position = LatLng(state.lat, state.lng)
        if (mapboxMap.markers.size > 0) {
            mapboxMap.removeMarker(userMarker.marker)
        }
        userMarker.position = position
        mapboxMap.addMarker(userMarker)

        mapboxMap.cameraPosition = CameraPosition.Builder()
                .target(position)
                .zoom(MAP_ZOOM)
                .build()
    }

    private fun onPointersReady(state: PointersReady) {
        val iconFactory = IconFactory.getInstance(this@MapScreen)
        val icon = iconFactory.fromBitmap(
                getBitmapFromVectorDrawable(this, R.drawable.ic_food_pin))

        mapboxMap.clear()
        mapboxMap.addMarker(userMarker)

        state.venues.forEach {
            mapboxMap.addMarker(MarkerOptions()
                    .position(LatLng(it.location.lat, it.location.lng))
                    .setTitle(it.name)
                    .setIcon(icon))
        }
    }

    private fun onErrorMessage(state: ErrorMessage) {
        state.e.printStackTrace()
        Snackbar.make(mapView, state.e.message ?: "error", Snackbar.LENGTH_SHORT).show()
    }
}