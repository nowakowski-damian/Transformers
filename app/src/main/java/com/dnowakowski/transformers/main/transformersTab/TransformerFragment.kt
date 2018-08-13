package com.dnowakowski.transformers.main.transformersTab

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dnowakowski.transformers.R
import com.dnowakowski.transformers.base.BaseFragment
import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.databinding.FragmentTransformersBinding
import com.dnowakowski.transformers.databinding.TransformerListItemBinding
import com.dnowakowski.transformers.injection.fragment.FragmentComponent
import com.dnowakowski.transformers.main.transformersTab.addDialog.AddTransformerDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class TransformerFragment: BaseFragment<FragmentTransformersBinding>() {

    companion object {
        private const val ARG_IMAGE = "ARG_IMAGE"
        private const val ARG_NAME = "ARG_NAME"
        private const val ARG_TYPE = "ARG_TYPE"

        fun newInstance(@DrawableRes image: Int, name: String, type: Transformer.Type): TransformerFragment {
            val fragment = TransformerFragment()
            fragment.arguments = Bundle().apply {
                putInt(ARG_IMAGE, image)
                putString(ARG_NAME, name)
                putString(ARG_TYPE,type.name)
            }
            return fragment
        }
    }

    @Inject
    lateinit var viewModel: TransformersViewModel

    private lateinit var transformersType: Transformer.Type

    override fun inject(component: FragmentComponent) {
        component.inject(this)
    }

    override fun bindData(binding: FragmentTransformersBinding) {
        binding.viewModel = viewModel
    }

    override fun provideLayout(): Int = R.layout.fragment_transformers

    override fun subscribeViewModel(): CompositeDisposable? {
        val eventDisposable = viewModel
                .events
                .subscribe(this@TransformerFragment::handleViewModelEvents)
        return CompositeDisposable(eventDisposable)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  super.onCreateView(inflater, container, savedInstanceState)
        binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@TransformerFragment.activity)
                    adapter = RecyclerAdapter( viewModel.transformers )
        }
        arguments?.apply {
            binding.imageView.setImageResource(getInt(ARG_IMAGE))
            binding.textView.text = getString(ARG_NAME)
            transformersType = Transformer.Type.valueOf(getString(ARG_TYPE))
            viewModel.fetchTransformers(transformersType)
        }
        return view
    }

    private fun handleViewModelEvents(event: TransformersEvent) {
        when(event) {
            is TransformersEvent.AddTransformer -> {
                val dialog = AddTransformerDialog.newInstance(transformersType)
                dialog.setCallback({ viewModel.fetchTransformers(transformersType) })
                dialog.show(fragmentManager, AddTransformerDialog::class.java.canonicalName)
            }
        }
    }
}

class RecyclerAdapter(trasformerSubject: PublishSubject<MutableList<Transformer>> ): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var transformers: MutableList<Transformer> = ArrayList()

    init {
        trasformerSubject.subscribe( {
            transformers = it
            notifyDataSetChanged()
        })
    }

    class ViewHolder(private val binding: TransformerListItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        fun bind(transformer: Transformer) {
            binding.transformer = transformer
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val itemBinding = TransformerListItemBinding.inflate(inflater,parent,false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = transformers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transformers[position])
    }
}