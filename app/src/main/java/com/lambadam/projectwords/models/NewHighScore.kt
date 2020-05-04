package com.lambadam.projectwords.models

/**
 * Bir level bittiğinde kullanıcı yeni bir yüksek skor kaydetmisse
 * Kullandığımız model
 */
data class NewHighScore (
    val score: Int,
    val position: Int,
    val difficulty: Difficulty
)