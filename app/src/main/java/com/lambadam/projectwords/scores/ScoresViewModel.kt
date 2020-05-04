package com.lambadam.projectwords.scores

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambadam.projectwords.BaseApplication
import com.lambadam.projectwords.models.Difficulty
import com.lambadam.projectwords.models.HighScore

class ScoresViewModel: ViewModel() {
    val scoresState: MutableLiveData<List<HighScore>> = MutableLiveData()
    val difficultiesState: MutableLiveData<List<Difficulty>> = MutableLiveData()

    fun getScores(difficulty: Difficulty) {
        scoresState.value = BaseApplication.INSTANCE.getPreferencesManager().getAllHighestScores(difficulty)
    }

    fun getDifficulties() {
        val values = Difficulty.values().asList()
        difficultiesState.value = values
    }

}