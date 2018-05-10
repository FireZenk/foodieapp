package org.firezenk.foodieapp.ui.features.map

import org.firezenk.foodieapp.configureRxThreading
import org.junit.Before
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.MockitoAnnotations

class MapPresenterTest {

    @Captor
    private lateinit var captor: ArgumentCaptor<States>

    init {
        configureRxThreading()
    }

    @Before
    fun setup() {
        createMocks()

    }

    private fun createMocks() {
        MockitoAnnotations.initMocks(this)

    }
}