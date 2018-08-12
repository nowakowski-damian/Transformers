package com.dnowakowski.transformers.injection.application

import com.dnowakowski.transformers.App
import dagger.Component

@ApplicationScope
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
    fun inject(app: App)
}