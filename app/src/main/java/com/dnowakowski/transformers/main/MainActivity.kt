package com.dnowakowski.transformers.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dnowakowski.transformers.R
import com.dnowakowski.transformers.base.BaseActivity
import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.databinding.ActivityMainBinding
import com.dnowakowski.transformers.injection.activity.ActivityComponent
import com.dnowakowski.transformers.main.tournamentTab.TournamentFragment
import com.dnowakowski.transformers.main.transformersTab.TransformerFragment
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
        binding.viewModel = viewModel
    }

    override fun subscribeViewModel(): CompositeDisposable? {
        val eventDisposable = viewModel
                .events
                .subscribe(this@MainActivity::handleViewModelEvents)
        return CompositeDisposable(eventDisposable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewPager.adapter = FragmentPageAdapter(this@MainActivity,supportFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    private fun handleViewModelEvents(event: MainEvent) {

    }
}


class FragmentPageAdapter(private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val name = context.resources.getStringArray(R.array.tab_names)[position]
        return when(position) {
            0 -> TransformerFragment.newInstance(R.drawable.autobot,name, Transformer.Type.AUTOBOT)
            1 -> TransformerFragment.newInstance(R.drawable.decepticon,name, Transformer.Type.DECEPTION)
            2 -> TournamentFragment()
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? =
            context.resources.getStringArray(R.array.tab_names)[position]


    override fun getCount(): Int = 3
}

