package com.dnowakowski.transformers.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dnowakowski.transformers.R
import com.dnowakowski.transformers.base.BaseActivity
import com.dnowakowski.transformers.databinding.ActivityMainBinding
import com.dnowakowski.transformers.injection.activity.ActivityComponent
import com.dnowakowski.transformers.main.autobotTab.AutobotFragment
import com.dnowakowski.transformers.main.deceptionTab.DeceptionFragment
import com.dnowakowski.transformers.main.tournamentTab.TournamentFragment
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
        binding.viewPager.adapter = FragmentPageAdapter(this,supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
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


class FragmentPageAdapter(private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> AutobotFragment()
            1 -> DeceptionFragment()
            2 -> TournamentFragment()
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? =
            context.resources.getStringArray(R.array.tab_names)[position]


    override fun getCount(): Int = 3
}

