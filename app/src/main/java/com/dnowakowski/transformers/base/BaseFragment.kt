package com.dnowakowski.transformers.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dnowakowski.transformers.injection.fragment.DaggerFragmentComponent
import com.dnowakowski.transformers.injection.fragment.FragmentComponent
import com.dnowakowski.transformers.injection.fragment.FragmentModule
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<BINDING: ViewDataBinding>: Fragment() {

    protected lateinit var binding: BINDING
    protected var subscription: CompositeDisposable? = null

    protected val component: FragmentComponent by lazy {
        DaggerFragmentComponent
                .builder()
                .fragmentModule(FragmentModule())
                .activityComponent( (activity as BaseActivity<*>).component )
                .build()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(component)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = initDataBinding(inflater, container)
        subscription = subscribeViewModel()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscription?.clear()
    }

    private fun initDataBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, provideLayout(), container, false)
        bindData(binding)
        binding.executePendingBindings()
        return binding.root
    }

    abstract fun inject(component: FragmentComponent)
    @LayoutRes abstract fun provideLayout(): Int
    abstract fun bindData(binding: BINDING)
    abstract fun subscribeViewModel(): CompositeDisposable?

}