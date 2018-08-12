package com.dnowakowski.transformers.main

import android.os.Bundle
import com.dnowakowski.transformers.R
import com.dnowakowski.transformers.base.BaseActivity
import com.dnowakowski.transformers.databinding.ActivityMainBinding
import com.dnowakowski.transformers.injection.activity.ActivityComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun inject(component: ActivityComponent) {
        component.inject(this)
    }

    override fun provideLayout(): Int = R.layout.activity_main

    override fun bindData(binding: ActivityMainBinding) {
    }

    override fun subscribeViewModel(): CompositeDisposable? {
        val eventDisposable = viewModel
                .events
                .subscribe(this@MainActivity::handleViewModelEvents)
        return CompositeDisposable(eventDisposable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun handleViewModelEvents(event: MainEvent) {

    }


}
