package com.lambadam.projectwords.util

import android.os.Build
import com.lambadam.projectwords.models.Difficulty
import com.lambadam.projectwords.models.HighScore

object GameUtil {
    //Kelimenin uzunluğu oyunun zorluğuna göre döner
    fun getWordLengthByDifficulty(difficulty: Difficulty): Int {
        return when(difficulty) {
            Difficulty.FirstLevel -> 3
            Difficulty.SecondLevel -> 4
            Difficulty.ThirdLevel -> 5
        }
    }

    //Kullanıcının kazandığı skoru belirlediğimiz method
    fun calculateScore(
        totalTimePassed: Long,totalWrongAttempt: Int,
        totalWordInDifficulty: Int, lengthOfWord : Int, foundedWord: Int
    ): Int {
        val initialScore = 300 * (0.3 * totalWordInDifficulty * lengthOfWord) + foundedWord * 50
        val wrongAttemptScore = 30 * totalWrongAttempt + (2 * totalTimePassed)

        val score = initialScore.toInt() - wrongAttemptScore.toInt()
        return  if(score < 0) 0 else score
    }

    fun sortHighestScores(scores: List<HighScore>): List<HighScore> {
        val comparator = Comparator { o1: HighScore, o2: HighScore ->  o2.score.compareTo(o1.score) }
        return if(scores.size <= 10) scores.sortedWith(comparator) else scores.sortedWith(comparator).subList(0,9)
    }

    fun getPositionOfScore(scores: List<HighScore>,yourScore: Int): Int {
        for(i in scores.indices) {
            if(scores[i].score == yourScore) return i+1
        }
        return -1
    }


    // Oyun zorluğunu ekrana yazmak icin kullanıdığımız method
    fun getDifficultyPrettyFormat(level: String): String {
        val levelNumber = level.substring(0,level.length-5)
        val levelString = level.substring(level.length-5,level.length)

        return String.format("%s %s", levelNumber, levelString)
    }
}