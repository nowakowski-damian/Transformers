package com.dnowakowski.transformers.injection.dialog

import com.dnowakowski.transformers.injection.activity.ActivityComponent
import com.dnowakowski.transformers.main.transformersTab.addDialog.AddTransformerDialog
import dagger.Component

@DialogScope
@Component(modules = [(DialogModule::class)], dependencies = [(ActivityComponent::class)] )
interface DialogComponent {
    fun inject(dialog: AddTransformerDialog)
}