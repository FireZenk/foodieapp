package org.firezenk.foodieapp.ui.features.map

import android.support.test.runner.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.firezenk.foodieapp.R
import org.firezenk.foodieapp.config.ProjectTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MapScreenTest {

    @get:Rule
    var rule = ProjectTestRule<MapScreen>(MapScreen::class.java, this)

    @Test
    fun onVisible_emptyTextIsDisplayed() {
        rule.launchActivity()

        assertDisplayed(R.string.app_name)
    }
}