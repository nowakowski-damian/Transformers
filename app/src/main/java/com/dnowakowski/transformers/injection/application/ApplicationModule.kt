package com.dnowakowski.transformers.injection.application

import com.dnowakowski.transformers.App
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule( private val app: App ) {

    @Provides
    @ApplicationScope
    fun provideApplication(): App = app

}