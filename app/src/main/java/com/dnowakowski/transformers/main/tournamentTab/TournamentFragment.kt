package com.dnowakowski.transformers.main.tournamentTab

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dnowakowski.transformers.R
import com.dnowakowski.transformers.base.BaseFragment
import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.databinding.FragmentTournamentBinding
import com.dnowakowski.transformers.injection.fragment.FragmentComponent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class TournamentFragment : BaseFragment<FragmentTournamentBinding>() {

    @Inject
    lateinit var viewModel: TournamentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  super.onCreateView(inflater, container, savedInstanceState)
        binding.autobotsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@TournamentFragment.activity)
            adapter = RecyclerAdapter(viewModel.autobots)
        }
        binding.decepticonsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@TournamentFragment.activity)
            adapter = RecyclerAdapter(viewModel.decepticons)
        }
        return view
    }


    override fun inject(component: FragmentComponent) {
        component.inject(this)
    }

    override fun provideLayout(): Int = R.layout.fragment_tournament

    override fun bindData(binding: FragmentTournamentBinding) {
        binding.viewModel = viewModel
    }

    override fun subscribeViewModel(): CompositeDisposable? {
        return CompositeDisposable()
    }
}

class RecyclerAdapter(trasformerSubject: PublishSubject<List<Transformer>>): RecyclerView.Adapter<com.dnowakowski.transformers.main.tournamentTab.RecyclerAdapter.ViewHolder>() {

    private var transformers: List<Transformer> = ArrayList()

    init {
        trasformerSubject.subscribe( {
            transformers = it
            notifyDataSetChanged()
        })
    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val textView = TextView(parent.context)
        return ViewHolder(textView)
    }

    override fun getItemCount(): Int = transformers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.textView.text = transformers[position].name
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = transformers[position].name
    }
}
