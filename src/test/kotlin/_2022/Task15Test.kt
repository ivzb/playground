package _2022

import org.junit.jupiter.api.Test
import utils.Point
import kotlin.math.exp
import kotlin.test.assertEquals

class Task15Test {

    @Test
    /**
     * ....# from ((0,-4)) to ((0,-4))
     * ...### from ((-1,-3)) to ((1,-3))
     * ..##### from ((-2,-2)) to ((2,-2))
     * .####### from ((-3,-1)) to ((3,-1))
     * ####SP### from ((-4,0)) to ((4,0))
     * .####### from ((-3,1)) to ((3,1))
     * ..##### from ((-2,2)) to ((2,2))
     * ...### from ((-1,3)) to ((1,3))
     * ....# from ((0,4)) to ((0,4)))
     */
    fun testMoveY1() {
        val sensor = Point(0, 0)
        val possible = Point(1, 0)
        val distance = 4

        val expected = 5
        val actual = Task15.moveIndex(sensor, possible, distance)

        assertEquals(expected, actual)
    }

    /**
     * ....# from ((0,-4)) to ((0,-4))
     * ...### from ((-1,-3)) to ((1,-3))
     * ..##### from ((-2,-2)) to ((2,-2))
     * .####### from ((-3,-1)) to ((3,-1))
     * ####S#### from ((-4,0)) to ((4,0))
     * .####P## from ((-3,1)) to ((3,1))
     * ..##### from ((-2,2)) to ((2,2))
     * ...### from ((-1,3)) to ((1,3))
     * ....# from ((0,4)) to ((0,4)))
     */
    @Test
    fun testMoveY2() {
        val sensor = Point(0, 0)
        val possible = Point(1, 1)
        val distance = 4

        val expected = 4
        val actual = Task15.moveIndex(sensor, possible, distance)

        assertEquals(expected, actual)
    }

    /**
     * .......# from ((20,-6)) to ((20,-6))
     * ......### from ((19,-5)) to ((21,-5))
     * .....##### from ((18,-4)) to ((22,-4))
     * ....####### from ((17,-3)) to ((23,-3))
     * ...######### from ((16,-2)) to ((24,-2))
     * ..########### from ((15,-1)) to ((25,-1))
     * .#P########### from ((14,0)) to ((26,0))
     * #######S####### from ((13,1)) to ((27,1))
     * .############# from ((14,2)) to ((26,2))
     * ..########### from ((15,3)) to ((25,3))
     * ...######### from ((16,4)) to ((24,4))
     * ....####### from ((17,5)) to ((23,5))
     * .....##### from ((18,6)) to ((22,6))
     * ......### from ((19,7)) to ((21,7))
     * .......# from ((20,8)) to ((20,8)))
     */
    @Test
    fun testMoveY3() {
        val sensor = Point(20, 1)
        val possible = Point(14, 0)
        val distance = 7

        val expected = 3
        val actual = Task15.moveIndex(sensor, possible, distance)

        assertEquals(expected, actual)
    }

    /**
     * .........# from ((8,-2)) to ((8,-2))
     * ........### from ((7,-1)) to ((9,-1))
     * .......##### from ((6,0)) to ((10,0))
     * ......####### from ((5,1)) to ((11,1))
     * .....######### from ((4,2)) to ((12,2))
     * ....########### from ((3,3)) to ((13,3))
     * ...############# from ((2,4)) to ((14,4))
     * ..############### from ((1,5)) to ((15,5))
     * .################# from ((0,6)) to ((16,6))
     * #########S######### from ((-1,7)) to ((17,7))
     * .################# from ((0,8)) to ((16,8))
     * ..############### from ((1,9)) to ((15,9))
     * ...############# from ((2,10)) to ((14,10))
     * ....########### from ((3,11)) to ((13,11))
     * .....######### from ((4,12)) to ((12,12))
     * ......####### from ((5,13)) to ((11,13))
     * .......##### from ((6,14)) to ((10,14))
     * ........### from ((7,15)) to ((9,15))
     * .........# from ((8,16)) to ((8,16)))
     */
    @Test
    fun testMoveY4() {
        val sensor = Point(8, 7)
        val possible = Point(14, 4)
        val distance = 9

        val expected = 0
        val actual = Task15.moveIndex(sensor, possible, distance)

        assertEquals(expected, actual)
    }

}