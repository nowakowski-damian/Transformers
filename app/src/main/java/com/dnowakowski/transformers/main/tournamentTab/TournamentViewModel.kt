package com.dnowakowski.transformers.main.tournamentTab

import android.databinding.ObservableField
import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.data.repository.TransformersRepository
import com.dnowakowski.transformers.injection.fragment.FragmentScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@FragmentScope
class TournamentViewModel @Inject constructor(
        private val transformersRepository: TransformersRepository,
        private val gameSimulator: GameSimulator
) {

    val numOfBattles = ObservableField<Int>(0)
    val winner = ObservableField<String>("None")

    val  autobots = PublishSubject.create<List<Transformer>>()
    val  decepticons = PublishSubject.create<List<Transformer>>()



    fun onFightButton() {
        transformersRepository.getTransformers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        gameSimulator
                                .simulate(it)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe( {
                                    numOfBattles.set(it.numOfBattles)
                                    winner.set(it.winner?.name.orEmpty())
                                    autobots.onNext(it.autobots.orEmpty())
                                    decepticons.onNext(it.decepticons.orEmpty())
                                })
                    }
        )

    }
}
