package com.lambadam.projectwords.models

import java.util.*

/**
 * Kelimeler icin kullandığımız model
 */
data class Word(
    val id: String = UUID.randomUUID().toString(),
    val value: String
)