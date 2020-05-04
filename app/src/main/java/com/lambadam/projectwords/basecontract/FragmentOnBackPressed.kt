package com.lambadam.projectwords.basecontract

/**
 * Uygulamada geri tuşuna basıldığında Fragment icinde bir değişiklik yapmak icin oluşturdugum
 * interface ScoresFragment implement eder Activity icinde bu method calıstırılır eger
 * Fragment ScoresFragment ise ve gerekli durum sağlanırsa
 */
interface FragmentOnBackPressed {
    fun onBackPressed(): Boolean
}