package com.dnowakowski.transformers.injection.fragment

import com.dnowakowski.transformers.injection.activity.ActivityComponent
import dagger.Component

@FragmentScope
@Component(modules = [(FragmentModule::class)], dependencies = [(ActivityComponent::class)] )
interface FragmentComponent {

}