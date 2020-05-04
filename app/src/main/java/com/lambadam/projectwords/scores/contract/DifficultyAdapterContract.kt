package com.lambadam.projectwords.scores.contract

import com.lambadam.projectwords.models.Difficulty

interface DifficultyAdapterContract {
    fun onDifficultyClick(difficulty: Difficulty)
}