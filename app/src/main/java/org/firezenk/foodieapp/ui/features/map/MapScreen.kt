package org.firezenk.foodieapp.ui.features.map

import android.Manifest
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import org.firezenk.foodieapp.App.Companion.component
import org.firezenk.foodieapp.R
import org.firezenk.foodieapp.ui.extensions.grantPermissions
import org.firezenk.foodieapp.ui.features.commons.Screen
import org.firezenk.foodieapp.ui.features.map.di.MapModule
import javax.inject.Inject
import com.mapbox.mapboxsdk.annotations.IconFactory
import kotlinx.android.synthetic.main.venue_detail.*
import kotlinx.android.synthetic.main.venues_map.*
import org.firezenk.foodieapp.ui.extensions.setVectorIcon
import org.firezenk.foodieapp.ui.utils.getBitmapFromVectorDrawable

class MapScreen : AppCompatActivity(), Screen<States> {

    companion object {
        private const val MAP_ZOOM = 15.0
    }

    @Inject lateinit var presenter: MapPresenter
    @Inject lateinit var actions: MapActions

    private lateinit var mapboxMap: MapboxMap
    private lateinit var sheetBehavior: BottomSheetBehavior<*>
    private val userMarker: MarkerOptions by lazy {
        MarkerOptions().apply {
            title = getString(R.string.user_marker_title)
            setVectorIcon(this@MapScreen, R.drawable.ic_user_pin)
        }
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
            is VenueReady -> onVenueReady(state)
            is ReservationChanged -> onReservationChanged(state)
            is ErrorMessage -> onErrorMessage(state)
        }
    }

    private fun initView(savedInstanceState: Bundle?) {
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        sheetBehavior.isHideable = true

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            initMap(it)
            askForPermissions()
        }
    }

    private fun initMap(it: MapboxMap) {
        mapboxMap = it
        mapboxMap.setOnMarkerClickListener {
            if (it.title != getString(R.string.user_marker_title)) {
                presenter reduce actions.openVenue(it.title)
                return@setOnMarkerClickListener true
            }
            return@setOnMarkerClickListener false
        }
        mapboxMap.addOnMapClickListener {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun askForPermissions() {
        grantPermissions(listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),
                { presenter reduce actions.loadVenues() })
    }

    private fun onPositionReady(state: PositionReady) {
        val position = LatLng(state.lat, state.lng)
        userMarker.position = position

        with(mapboxMap) {
            addMarker(userMarker)
            cameraPosition = CameraPosition.Builder()
                    .target(position)
                    .zoom(MAP_ZOOM)
                    .build()
        }
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

    private fun onVenueReady(state: VenueReady) {
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        venueName.text = state.venue.name
        venueAddress.text = getString(R.string.venue_address, state.venue.location.address,
                state.venue.location.postalCode, state.venue.location.city)

        changeReservationButtons(state.venue.reserved)

        venueMakeReservation.setOnClickListener {
            presenter reduce actions.makeReservation(state.venue.id)
        }

        venueCancelReservation.setOnClickListener {
            presenter reduce actions.cancelReservation(state.venue.id)
        }

        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun onReservationChanged(state: ReservationChanged) {
        changeReservationButtons(state.reserved)
    }

    private fun onErrorMessage(state: ErrorMessage) {
        state.e.printStackTrace()
        Snackbar.make(mapView, state.e.message ?: "error", Snackbar.LENGTH_SHORT).show()
    }

    private fun changeReservationButtons(reserved: Boolean) {
        if (reserved) {
            venueMakeReservation.visibility = View.GONE
            venueCancelReservation.visibility = View.VISIBLE
        } else {
            venueMakeReservation.visibility = View.VISIBLE
            venueCancelReservation.visibility = View.GONE
        }
    }
}