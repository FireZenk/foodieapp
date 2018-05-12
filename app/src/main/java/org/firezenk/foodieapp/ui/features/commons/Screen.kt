package org.firezenk.foodieapp.ui.features.commons

interface Screen<in S : State> {

    fun render(state: S)
}