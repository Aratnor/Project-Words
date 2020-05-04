package com.lambadam.projectwords.models

/**
 * Oyun Durumunu kaydetmek icin kullandığımız model
 */
data class CurrentGameState(
    val currentLevel: Int,
    val currentSubLevel: Int,
    val foundedWords: List<String>,
    val failedAttempt: Int,
    val currentScore: Int,
    val totalScore: Int,
    val currentTime: Int
    )