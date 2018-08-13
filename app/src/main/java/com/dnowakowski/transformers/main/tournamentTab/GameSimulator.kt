package com.dnowakowski.transformers.main.tournamentTab


import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.injection.fragment.FragmentScope
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

@FragmentScope
class GameSimulator @Inject constructor() {

    private val transformerComparator: Comparator<Transformer>
    private val autobots: PriorityQueue<Transformer>
    private val deceptions: PriorityQueue<Transformer>

    init {
        transformerComparator = Comparator { o1, o2 -> o2.rank - o1.rank }
        autobots = PriorityQueue(1,transformerComparator)
        deceptions = PriorityQueue(1,transformerComparator)
    }


    private fun getWinner(transformer1: Transformer, transformer2: Transformer): Transformer? {

        //        Any Transformer named Optimus Prime or Predaking wins his fight automatically
        //        regardless of any other criteria
        val isTransformer1Special = transformer1.name==Transformer.OPTIMUS_PRIME || transformer1.name==Transformer.PREDAKING
        val isTransformer2Special = transformer2.name==Transformer.OPTIMUS_PRIME || transformer2.name==Transformer.PREDAKING
        if (isTransformer1Special && isTransformer2Special) {
            throw BothTransformersSpecialException()
        } else if (isTransformer1Special) {
            return transformer1
        } else if (isTransformer2Special) {
            return transformer2
        }

        //        If any fighter is down 4 or more points of courage and 3 or more points of strength
        //        compared to their opponent, the opponent automatically wins the face-off regardless of
        //        overall rating (opponent has ran away)
        val courageDiff = transformer1.courage - transformer2.courage
        val strengthDiff = transformer1.strength - transformer2.strength
        if (courageDiff >= 4 && strengthDiff >= 3) {
            return transformer1
        } else if (courageDiff <= -4 && strengthDiff <= -3) {
            return transformer2
        }

        //        Otherwise, if one of the fighters is 3 or more points of skill above their opponent, they win
        //        the fight regardless of overall rating
        val skillDiff = transformer1.skill - transformer2.skill
        if (skillDiff >= 3) {
            return transformer1
        } else if (skillDiff <= -3) {
            return transformer2
        }

        //        The winner is the Transformer with the highest overall rating
        //        In the event of a tie, both Transformers are considered destroyed
        val overallDiff = transformer1.overallRating - transformer2.overallRating
        return if (overallDiff == 0) {
            null
        } else {
            if (overallDiff > 0) transformer1 else transformer2
        }

    }

    fun simulate(transformerList: List<Transformer>): Observable<GameResult> {
        for(transformer in transformerList) {
            if(transformer.type==Transformer.Type.AUTOBOT) {
                autobots.add(transformer)
            }
            else {
                deceptions.add(transformer)
            }
        }

        var numberOfBattles = 0
        val autobotWinners = LinkedList<Transformer>()
        val deceptionWinners = LinkedList<Transformer>()

        while (!autobots.isEmpty() && !deceptions.isEmpty()) {
            ++numberOfBattles
            val autobot = autobots.poll()
            val deception = deceptions.poll()
            try {
                getWinner(autobot, deception)?.apply {
                    if(isAutobot) {
                        autobotWinners.add(this)
                    } else {
                        deceptionWinners.add(this)
                    }
                }
            }
            catch (exception: BothTransformersSpecialException) {
                exception.printStackTrace()
                return Observable.just(GameResult(numberOfBattles,null,null,null))
            }
        }

        val winner: Transformer.Type? = if(autobotWinners.size>deceptionWinners.size) {
            Transformer.Type.AUTOBOT
        }
        else if(autobotWinners.size<deceptionWinners.size) {
            Transformer.Type.DECEPTION
        }
        else {
            null
        }

        // add remaining players to winner list
        autobotWinners.addAll(autobots)
        deceptionWinners.addAll(deceptions)
        autobots.clear()
        deceptions.clear()

        return Observable.just(GameResult(numberOfBattles,winner, autobotWinners, deceptionWinners))
    }

}

class GameResult(val numOfBattles:Int, val winner: Transformer.Type?, val autobots: LinkedList<Transformer>?, val decepticons: LinkedList<Transformer>?)

class BothTransformersSpecialException: Exception("The game immediately ends with all competitors destroyed")