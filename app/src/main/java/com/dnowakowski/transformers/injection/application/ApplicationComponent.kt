package com.dnowakowski.transformers.injection.application

import com.dnowakowski.transformers.App
import com.dnowakowski.transformers.data.repository.TransformersRepository
import dagger.Component

@ApplicationScope
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
    fun inject(app: App)

    fun transformersRepository(): TransformersRepository
}