package com.dnowakowski.transformers.main.transformersTab.addDialog

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dnowakowski.transformers.R
import com.dnowakowski.transformers.base.BaseActivity
import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.databinding.DialogAddTransformerBinding
import com.dnowakowski.transformers.injection.dialog.DaggerDialogComponent
import com.dnowakowski.transformers.injection.dialog.DialogComponent
import com.dnowakowski.transformers.injection.dialog.DialogModule
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AddTransformerDialog: DialogFragment() {

    companion object {
        private const val ARG_TYPE = "ARG_TYPE"

        fun newInstance(transformerType: Transformer.Type): AddTransformerDialog {
            val dialog = AddTransformerDialog()
            dialog.arguments = Bundle().apply {
                putString(ARG_TYPE,transformerType.name)
            }
            return dialog
        }
    }

    @Inject
    lateinit var viewModel: AddTransformerDialogViewModel
    private var subscription: CompositeDisposable? = null
    private var callback: ()->Unit = {}

    protected val component: DialogComponent by lazy {
        DaggerDialogComponent
                .builder()
                .dialogModule(DialogModule())
                .activityComponent( (activity as BaseActivity<*>).component )
                .build()

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val eventDisposable = viewModel
                .events
                .subscribe(this@AddTransformerDialog::handleViewModelEvents)
        subscription = CompositeDisposable(eventDisposable)
        val binding: DialogAddTransformerBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_transformer, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        arguments?.apply {
            val transformersType = Transformer.Type.valueOf(getString(ARG_TYPE))
            viewModel.type.set(transformersType)
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscription?.clear()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    private fun handleViewModelEvents(event: AddTransformerDialogEvent) {
        when(event) {
            is AddTransformerDialogEvent.TransformerSaved -> {
                dismiss()
                callback.invoke()
            }
        }
    }

    fun setCallback(callback: () -> Unit) {
        this@AddTransformerDialog.callback = callback
    }

}