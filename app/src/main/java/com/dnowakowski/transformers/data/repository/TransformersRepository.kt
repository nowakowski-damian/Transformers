package com.dnowakowski.transformers.data.repository

import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.injection.application.ApplicationScope
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@ApplicationScope
class TransformersRepository @Inject constructor() {

    val decepticons: MutableList<Transformer>
    val autobots: MutableList<Transformer>

    init {
        decepticons = generateDecepticonsDump()
        autobots = generateAutobotsDump()
    }

    //todo: remove dump function
    private fun generateDecepticonsDump(): MutableList<Transformer> {
        val list = ArrayList<Transformer>()
        list.add(
                Transformer("Soundwave",
                        Transformer.Type.DECEPTION,
                        8,
                        9,
                        2,
                        6,
                        7,
                        5,
                        6,
                        10)
        )
        return list
    }
    private fun generateAutobotsDump(): MutableList<Transformer> {
        val list = ArrayList<Transformer>()
        list.add(
                Transformer("Bluestreak",
                        Transformer.Type.AUTOBOT,
                        6,
                        6,
                        7,
                        9,
                        5,
                        2,
                        9,
                        7)
        )
        list.add(
                Transformer("Hubcap",
                        Transformer.Type.AUTOBOT,
                        4,
                        4,
                        4,
                        4,
                        4,
                        4,
                        4,
                        4)
        )
        list.add(
                Transformer("Hubcap2",
                        Transformer.Type.AUTOBOT,
                        4,
                        4,
                        4,
                        4,
                        4,
                        4,
                        4,
                        4)
        )
        list.add(
                Transformer("Hubcap3",
                        Transformer.Type.AUTOBOT,
                        4,
                        4,
                        4,
                        4,
                        4,
                        4,
                        4,
                        4)
        )
        return list
    }


    fun getAutobots(): Observable<MutableList<Transformer> > =  Observable.just(autobots)

    fun getDecepticons(): Observable< MutableList<Transformer> > = Observable.just(decepticons)

    fun saveTransformer(transformer: Transformer): Observable<Transformer> {
        if(transformer.type==Transformer.Type.AUTOBOT) {
            autobots.add(transformer)
        }
        else {
            decepticons.add(transformer)
        }
        return Observable.just(transformer)
    }

}