package com.dnowakowski.transformers

import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.main.tournamentTab.GameSimulator
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.util.*


class GameSimulatorTest {

    lateinit var gameplay: GameSimulator

    @Before
    fun setUp() {
        gameplay = GameSimulator()
    }

    @Test
    fun bothTransformersAreSpecial() {
        gameplay
                .simulate( Arrays.asList(
                        Transformer("Optimus Prime",
                                com.dnowakowski.transformers.data.model.Transformer.Type.AUTOBOT,
                                8,
                                9,
                                2,
                                6,
                                7,
                                5,
                                6,
                                10),
                        Transformer("Predaking",
                                com.dnowakowski.transformers.data.model.Transformer.Type.DECEPTION,
                                8,
                                9,
                                2,
                                6,
                                7,
                                5,
                                6,
                                10)
                ) )
                .subscribe({
                    assertEquals(1,it.numOfBattles)
                    assertNull(it.winner)
                    assertNull(it.autobots)
                    assertNull(it.decepticons)
                })
    }



    @Test
    fun notEqualNumOfTransformersTest() {
        gameplay
                .simulate( generateTransformersFromDocExample() )
                .subscribe({
                    assertEquals(1,it.numOfBattles)
                    assertEquals(Transformer.Type.DECEPTION,it.winner)
                    assertEquals(1,it.autobots?.size)
                    assertEquals(1,it.decepticons?.size)
                })
    }


    @Test
    fun oneTeamIsEmpty() {
        gameplay
                .simulate( Arrays.asList(
                        Transformer("Optimus Prime",
                                com.dnowakowski.transformers.data.model.Transformer.Type.AUTOBOT,
                                8,
                                9,
                                2,
                                6,
                                7,
                                5,
                                6,
                                10)
                ) )
                .subscribe({
                    assertEquals(0,it.numOfBattles)
                    assertNull(it.winner)
                    assertEquals(1,it.autobots?.size)
                    assertEquals(0,it.decepticons?.size)
                })
    }


    @Test
    fun tie() {
        gameplay
                .simulate( Arrays.asList(
                        Transformer("Optimus Not Prime",
                                com.dnowakowski.transformers.data.model.Transformer.Type.AUTOBOT,
                                8,
                                9,
                                2,
                                6,
                                7,
                                5,
                                6,
                                10),
                        Transformer("Optimus Not Prime",
                                com.dnowakowski.transformers.data.model.Transformer.Type.DECEPTION,
                                8,
                                9,
                                2,
                                6,
                                7,
                                5,
                                6,
                                10)
                ) )
                .subscribe({
                    assertEquals(1,it.numOfBattles)
                    assertNull(it.winner)
                    assertEquals(0,it.autobots?.size)
                    assertEquals(0,it.decepticons?.size)
                })
    }


    private fun generateTransformersFromDocExample(): MutableList<Transformer> {
        val list = ArrayList<com.dnowakowski.transformers.data.model.Transformer>()
        list.add(
                com.dnowakowski.transformers.data.model.Transformer("Soundwave",
                        com.dnowakowski.transformers.data.model.Transformer.Type.DECEPTION,
                        8,
                        9,
                        2,
                        6,
                        7,
                        5,
                        6,
                        10)
        )
        list.add(
                com.dnowakowski.transformers.data.model.Transformer("Bluestreak",
                        com.dnowakowski.transformers.data.model.Transformer.Type.AUTOBOT,
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
                com.dnowakowski.transformers.data.model.Transformer("Hubcap",
                        com.dnowakowski.transformers.data.model.Transformer.Type.AUTOBOT,
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

}
