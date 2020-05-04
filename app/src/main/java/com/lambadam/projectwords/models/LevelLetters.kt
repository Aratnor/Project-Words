package com.lambadam.projectwords.models

import java.util.*

/**
 * Bu alt zorluk icinde ki bulmacanın bilgilerini tutuğumuz model
 */
data class LevelLetters(
    val letterId: String = UUID.randomUUID().toString(),
    val letterList: List<Char>,
    val words: List<Word> = emptyList(),
    val difficulty: Difficulty = Difficulty.FirstLevel,
    val subDifficulty: SubDifficulty = SubDifficulty.FirstLevel
)