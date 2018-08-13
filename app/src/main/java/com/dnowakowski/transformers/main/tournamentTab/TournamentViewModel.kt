package com.dnowakowski.transformers.main.tournamentTab

import android.databinding.ObservableField
import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.data.repository.TransformersRepository
import com.dnowakowski.transformers.injection.fragment.FragmentScope
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@FragmentScope
class TournamentViewModel @Inject constructor(
        private val transformersRepository: TransformersRepository
) {

    val numOfBattles = ObservableField<Int>(0)
    val winner = ObservableField<String>("None")

    val  autobots = PublishSubject.create<MutableList<Transformer>>()
    val  decepticons = PublishSubject.create<MutableList<Transformer>>()



    fun onFightButton() {

    }


}