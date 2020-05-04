package com.lambadam.projectwords.models

/**
 * Kullanıcı bir kelime girdiginde kullandığımız model
 */
data class AddWord(
    val value: String,
    val isReEnteredWord: Boolean,
    val isWordExists: Boolean,
    val isLevelChanged: Boolean = false
)