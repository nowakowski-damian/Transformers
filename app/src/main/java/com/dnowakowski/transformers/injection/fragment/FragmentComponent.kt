package com.dnowakowski.transformers.injection.fragment

import com.dnowakowski.transformers.injection.activity.ActivityComponent
import com.dnowakowski.transformers.main.tournamentTab.TournamentFragment
import com.dnowakowski.transformers.main.transformersTab.TransformerFragment
import dagger.Component

@FragmentScope
@Component(modules = [(FragmentModule::class)], dependencies = [(ActivityComponent::class)] )
interface FragmentComponent {
    fun inject(fragment: TransformerFragment)
    fun inject(fragment: TournamentFragment)
}