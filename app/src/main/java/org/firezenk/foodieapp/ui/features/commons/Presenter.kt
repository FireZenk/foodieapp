package org.firezenk.foodieapp.ui.features.commons

import io.reactivex.disposables.CompositeDisposable

abstract class Presenter<in A : Action, S : State> {

    protected val disposables: CompositeDisposable = CompositeDisposable()

    private lateinit var screen: Screen<S>

    open infix fun init(screen: Screen<S>) {
        this.screen = screen
    }

    abstract infix fun reduce(action: A)

    infix fun render(state: S) = screen.render(state)

    fun destroy() = disposables.clear()
}