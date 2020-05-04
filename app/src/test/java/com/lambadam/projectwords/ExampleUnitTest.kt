package com.lambadam.projectwords

import com.lambadam.projectwords.models.HighScore
import com.lambadam.projectwords.util.GameUtil
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testSortList() {
        val scoreList = listOf(
            HighScore(3,"A"),
            HighScore(5,"B"),
            HighScore(4,"C"),
            HighScore(6,"A"),
            HighScore(7,"A"))

        print(GameUtil.sortHighestScores(scoreList).toString())
    }

    @Test
    fun findRankOfScore() {
        val list = emptyList<Int>().toMutableList()
        list.add(5)

        assertEquals(5,list[0])

    }

}
