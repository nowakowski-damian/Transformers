package com.dnowakowski.transformers.injection.activity

import com.dnowakowski.transformers.data.repository.TransformersRepository
import com.dnowakowski.transformers.injection.application.ApplicationComponent
import com.dnowakowski.transformers.main.MainActivity
import dagger.Component

@ActivityScope
@Component(modules = [(ActivityModule::class)], dependencies = [(ApplicationComponent::class)])
interface ActivityComponent {
    fun inject(activity: MainActivity)

    fun transformersRepository(): TransformersRepository

}