package com.dnowakowski.transformers.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.dnowakowski.transformers.App
import com.dnowakowski.transformers.injection.activity.ActivityComponent
import com.dnowakowski.transformers.injection.activity.ActivityModule
import com.dnowakowski.transformers.injection.activity.DaggerActivityComponent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<BINDING: ViewDataBinding>: AppCompatActivity() {

    protected lateinit var binding: BINDING
    protected var subscription: CompositeDisposable? = null

    val component: ActivityComponent by lazy {
        DaggerActivityComponent
                .builder()
                .activityModule(ActivityModule(this))
                .applicationComponent( (application as App).component )
                .build()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(component)
        initDataBinding()
        subscription = subscribeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription?.clear()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, provideLayout())
        bindData(binding)
        binding.executePendingBindings()
    }

    abstract fun inject(component: ActivityComponent)
    @LayoutRes abstract fun provideLayout(): Int
    abstract fun bindData(binding: BINDING)
    abstract fun subscribeViewModel(): CompositeDisposable?


}