package org.firezenk.foodieapp.config

import android.support.test.InstrumentationRegistry
import it.cosenonjaviste.daggermock.DaggerMock
import org.firezenk.foodieapp.App
import org.firezenk.foodieapp.di.AppComponent
import org.firezenk.foodieapp.di.module.AppModule

fun getDaggerRule() = DaggerMock.rule<AppComponent>(AppModule(app)) {
    set { component ->
        app.updateComponent(component)
    }
}

val app: App get() = InstrumentationRegistry.getTargetContext().applicationContext as App