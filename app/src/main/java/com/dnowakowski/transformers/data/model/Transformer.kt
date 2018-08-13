package com.dnowakowski.transformers.data.model

class Transformer(val name: String,
                  val type: Type,
                  strength: Int,
                  intelligence: Int,
                  speed: Int,
                  rank: Int,
                  endurance: Int,
                  courage: Int,
                  firepower: Int,
                  skill: Int
) {
    val strength: Int
    val intelligence: Int
    val speed: Int
    val rank: Int
    val endurance: Int
    val courage: Int
    val firepower: Int
    val skill: Int

    val overallRating: Int

    val isAutobot: Boolean
        get() = type == Type.AUTOBOT

    val isDeception: Boolean
        get() = type == Type.DECEPTION

    init {
        this.strength = normalize(strength)
        this.intelligence = normalize(intelligence)
        this.speed = normalize(speed)
        this.rank = normalize(rank)
        this.endurance = normalize(endurance)
        this.courage = normalize(courage)
        this.firepower = normalize(firepower)
        this.skill = normalize(skill)
        this.overallRating = strength + intelligence + speed + endurance + firepower
    }

    private fun normalize(value: Int): Int {
        return Math.max(Math.min(value, MAX_CRITERIA_VALUE), MIN_CRITERIA_VALUE)
    }

    companion object {

        private val MAX_CRITERIA_VALUE = 10
        private val MIN_CRITERIA_VALUE = 1

        val OPTIMUS_PRIME = "Optimus Prime"
        val PREDAKING = "Predaking"
    }

    enum class Type {
        AUTOBOT,
        DECEPTION
    }
}
