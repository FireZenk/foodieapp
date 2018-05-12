package org.firezenk.foodieapp.ui.features.map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.screen_map.*
import org.firezenk.foodieapp.App.Companion.component
import org.firezenk.foodieapp.R
import org.firezenk.foodieapp.ui.features.commons.Screen
import javax.inject.Inject

class MapScreen : AppCompatActivity(), Screen<States> {

    @Inject lateinit var presenter: MapPresenter
    @Inject lateinit var actions: MapActions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_map)

        component inject this

        presenter init this

        mapView.onCreate(savedInstanceState)
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

    }
}