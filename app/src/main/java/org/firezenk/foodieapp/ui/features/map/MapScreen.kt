package org.firezenk.foodieapp.ui.features.map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun render(state: States) {

    }
}