package com.lambadam.projectwords.playscene.contract

interface OnAdapterUpdate {
    //Kelimenin tamamını girdiğinde
    fun onFinish(isWordFound: Boolean,word: String)

    //Bir harf sectiğinde
    fun onSelectedChar(value: String)

    //Sectiği harfi deselect yaptığında
    fun deselectedChar(value: String)
}