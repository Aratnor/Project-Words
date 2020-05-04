package com.lambadam.projectwords.util

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lambadam.projectwords.models.CurrentGameState
import com.lambadam.projectwords.models.Difficulty
import com.lambadam.projectwords.models.HighScore
import java.lang.reflect.Type

/**
 *primitive ve String typeları localde xmlde mapleyerek tutan local Storage: SharedPreferences
 *icin oluşturulmuş bir yapı kullanıcı adını ve scoreları ve yarıda cıkılmıs oyunun durumunu kaydedip cekmek icin kullanıyoruz
 */
class PreferencesManager(private val sharedPreferences: SharedPreferences) {
    companion object {
        private const val USER_NAME = "username"
        private const val HIGHEST_SCORES_FIRST_LEVEL = "scores_first_level"
        private const val HIGHEST_SCORES_SECOND_LEVEL = "scores_second_level"
        private const val HIGHEST_SCORES_THIRD_LEVEL = "scores_third_level"
        private const val CURRENT_GAME_STATE = "current_game_state"
        private const val IS_CURRENT_GAME_STATE_SAVED = "is_current_game_state_saved"
    }

    fun setUserName(userName: String) {
        sharedPreferences.edit().putString(USER_NAME,userName).apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString(USER_NAME, "")!!
    }

    fun updateHighestScores(scores: List<HighScore>,difficulty: Difficulty) {
        val jsonValue = Gson().toJson(scores)

        sharedPreferences.edit().putString(getKeyFromDifficulty(difficulty),jsonValue).apply()
    }

    fun getAllHighestScores(difficulty: Difficulty): List<HighScore> {
        val jsonValue = sharedPreferences.getString(getKeyFromDifficulty(difficulty), "")
        if(jsonValue!!.isNotEmpty()) {
            val listType: Type = object : TypeToken<List<HighScore>>() {}.type
            return Gson().fromJson(jsonValue, listType)
        }
        return emptyList()
    }

    fun saveCurrentGameState(currentGameState: CurrentGameState) {
        val jsonValue = Gson().toJson(currentGameState)
        sharedPreferences.edit().putString(CURRENT_GAME_STATE,jsonValue).apply()
        sharedPreferences.edit().putBoolean(IS_CURRENT_GAME_STATE_SAVED,true).apply()
    }

    fun getCurrentGameState(): CurrentGameState? {
        val jsonValue = sharedPreferences.getString(CURRENT_GAME_STATE, "")
        if(jsonValue!!.isNotEmpty()) {
            return Gson().fromJson(jsonValue, CurrentGameState::class.java)
        }
        return null
    }

    fun isCurrentGameStateSaved(): Boolean {
        return sharedPreferences.getBoolean(IS_CURRENT_GAME_STATE_SAVED,false)
    }

    private fun getKeyFromDifficulty(difficulty: Difficulty): String {
        return when(difficulty) {
            Difficulty.FirstLevel -> HIGHEST_SCORES_FIRST_LEVEL
            Difficulty.SecondLevel -> HIGHEST_SCORES_SECOND_LEVEL
            Difficulty.ThirdLevel -> HIGHEST_SCORES_THIRD_LEVEL
        }
    }
}