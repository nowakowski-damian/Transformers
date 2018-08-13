package com.dnowakowski.transformers.data.repository

import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.injection.application.ApplicationScope
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@ApplicationScope
class TransformersRepository @Inject constructor() {

    val transformers: MutableList<Transformer> = ArrayList()

    fun getTransformers(): Observable<MutableList<Transformer> > = Observable.just(transformers)

    fun getAutobots(): Single<MutableList<Transformer>> =
            Observable.just(transformers)
                    .flatMap{ Observable.fromIterable(it) }
                    .filter {
                        it.type==Transformer.Type.AUTOBOT
                    }
                    .toList()

    fun getDecepticons(): Single<MutableList<Transformer>> =
            Observable.just(transformers)
                    .flatMap{ Observable.fromIterable(it) }
                    .filter {
                        it.type==Transformer.Type.DECEPTION
                    }
                    .toList()
    fun saveTransformer(transformer: Transformer): Observable<Transformer> {
        transformers.add(transformer)
        return Observable.just(transformer)
    }

}