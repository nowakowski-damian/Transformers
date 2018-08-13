package com.dnowakowski.transformers.main.transformersTab

import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.data.repository.TransformersRepository
import com.dnowakowski.transformers.injection.fragment.FragmentScope
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@FragmentScope
class TransformersViewModel @Inject constructor(
        private val transformersRepository: TransformersRepository) {

    val events = PublishSubject.create<TransformersEvent>()
    val  transformers = PublishSubject.create<MutableList<Transformer>>()

    fun onAddTransformerFab() {
        events.onNext(TransformersEvent.AddTransformer())
    }

    fun fetchTransformers(type: Transformer.Type) {
        if( type==Transformer.Type.AUTOBOT) {
            transformersRepository.getAutobots()
                    .subscribe(
                            { transformers.onNext(it) },
                            {
                                it.printStackTrace()
                            }
                    )
        } else {
            transformersRepository.getDecepticons()
                    .subscribe(
                            { transformers.onNext(it) },
                            {
                                it.printStackTrace()
                            }
                    )
        }
    }

}

sealed class TransformersEvent {
    class AddTransformer: TransformersEvent()
}