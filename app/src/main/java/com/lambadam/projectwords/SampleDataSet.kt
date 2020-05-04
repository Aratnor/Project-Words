package com.lambadam.projectwords

import com.lambadam.projectwords.models.Difficulty
import com.lambadam.projectwords.models.LevelLetters
import com.lambadam.projectwords.models.SubDifficulty
import com.lambadam.projectwords.models.Word

/**
 * Kotlinde fonksiyon yazmak icin
 * static method gibi provideLettersLevelOne() olarak dışardan ulaşılabilir
 * Kotlin gerekli importları yapar o yüzden bu method her yerden ulaşılabilirü8ğ766
 */
fun provideLettersLevelOne(): List<LevelLetters> {
    return listOf(
        LevelLetters(
            letterList = listOf('b','a','l'),
            words = listOf(Word(value = "BAL")),
            difficulty = Difficulty.FirstLevel,
            subDifficulty =  SubDifficulty.FirstLevel),
        LevelLetters(
            letterList = listOf('a','h','k'),
            words = listOf(Word(value = "KAH"), Word(value = "HAK")),
            difficulty = Difficulty.FirstLevel,
            subDifficulty = SubDifficulty.SecondLevel
        ),
        LevelLetters(
            letterList = listOf('k','a','ş'),
            words = listOf(Word(value = "KAŞ"),Word(value = "ŞAK"), Word(value = "AŞK")),
            difficulty = Difficulty.FirstLevel,
            subDifficulty = SubDifficulty.ThirdLevel
        ),
        LevelLetters(
            letterList = listOf('a','k','r','n'),
            words = listOf(Word(value = "KAR"), Word(value = "ARK"), Word(value = "KAN"), Word(value = "NAR")),
            difficulty = Difficulty.FirstLevel,
            subDifficulty = SubDifficulty.FourthLevel
        ),
        LevelLetters(
            letterList = listOf('y','a','k','o'),
            words = listOf(
                Word(value = "KAY"), Word(value = "YAK"), Word(value = "KOY"),
                Word(value = "YOK"), Word(value = "OYA")),
            difficulty = Difficulty.FirstLevel,
            subDifficulty = SubDifficulty.FifthLevel
        ),
        LevelLetters(
            letterList = listOf('t','a','m','i','t'),
            words = listOf(
                Word(value = "TAM"), Word(value = "MAT"),
                Word(value = "TİM"), Word(value = "MİT"), Word(value = "İMA")),
            difficulty = Difficulty.FirstLevel,
            subDifficulty = SubDifficulty.SixthLevel
            )
    )
}

fun provideLettersLevelTwo(): List<LevelLetters> {
    return listOf(
        LevelLetters(
            letterList = listOf('n','n','a','e'),
            words = listOf(
                Word(value = "ANNE")
            ),
            difficulty = Difficulty.SecondLevel,
            subDifficulty = SubDifficulty.FirstLevel
        ),
        LevelLetters(
            letterList = listOf('a','y','k','a'),
            words = listOf(
                Word(value = "AYAK"), Word(value = "KAYA")
            ),
            difficulty = Difficulty.SecondLevel,
            subDifficulty = SubDifficulty.SecondLevel
        ),
        LevelLetters(
            letterList = listOf('a','k','t','ı'),
            words = listOf(
                Word(value = "ATIK"), Word(value = "KITA"), Word(value = "ATKI")
            ),
            difficulty = Difficulty.SecondLevel,
            subDifficulty = SubDifficulty.ThirdLevel
        ),
        LevelLetters(
            letterList = listOf('a','l','e','v'),
            words = listOf(
                Word(value = "ALEV"), Word(value = "VALE"), Word(value = "EVLA"), Word(value = "LEVA")
            ),
            difficulty = Difficulty.SecondLevel,
            subDifficulty = SubDifficulty.FourthLevel
        ),
        LevelLetters(
            letterList = listOf('k','l','a','p','e'),
            words = listOf(
                Word(value = "PLAK"), Word(value = "KALP"),
                 Word(value = "KALE"), Word(value = "LAKE")
            ),
            difficulty = Difficulty.SecondLevel,
            subDifficulty = SubDifficulty.FifthLevel
        ),
        LevelLetters(
            letterList = listOf('m','a','i','n'),
            words = listOf(
                Word(value = "AMİN"), Word(value = "İMAN"), Word(value = "MANİ"), Word(value = "MAİN")
            ),
            difficulty = Difficulty.SecondLevel,
            subDifficulty = SubDifficulty.SixthLevel
        )
    )
}

fun provideLettersLevelThree(): List<LevelLetters> {
    return listOf(
        LevelLetters(
            letterList = listOf('m','a','l','r','u'),
            words = listOf(
                Word(value = "MARUL")
            ),
            difficulty = Difficulty.ThirdLevel,
            subDifficulty = SubDifficulty.FirstLevel
        ),
        LevelLetters(
            letterList = listOf('k','l','i','m','a','e'),
            words = listOf(
                Word(value = "EMLAk"), Word(value = "KALEM"), Word(value = "KLİMA")
            ),
            difficulty = Difficulty.ThirdLevel,
            subDifficulty = SubDifficulty.SecondLevel
        ),
        LevelLetters(
            letterList = listOf('m','e','r','a','c','k'),
            words = listOf(
                Word(value = "MECRA"), Word(value = "MERAK"), Word(value = "KREMA")
            ),
            difficulty = Difficulty.ThirdLevel,
            subDifficulty = SubDifficulty.ThirdLevel
        ),
        LevelLetters(
            letterList = listOf('s','e','k','e','n','i'),
            words = listOf(
                Word(value = "EKSEN"), Word(value = "ESNEK"), Word(value = "KESEN"),
                Word(value = "SİNEK")
            ),
            difficulty = Difficulty.ThirdLevel,
            subDifficulty = SubDifficulty.FourthLevel
        ),
        LevelLetters(
            letterList = listOf('d','i','a','r','e','l'),
            words = listOf(
                Word(value = "İDARE"), Word(value = "DAİRE"), Word(value = "İRADE"),
                Word(value = "LİDER"), Word(value = "İDEAL")
            ),
            difficulty = Difficulty.ThirdLevel,
            subDifficulty = SubDifficulty.FifthLevel
        ),
        LevelLetters(
            letterList = listOf('ç','a','k','ı','l','a'),
            words = listOf(
                Word(value = "AÇLIK"), Word(value = "ÇAKIL"), Word(value = "ÇAKAL"),
                Word(value = "ALÇAK"), Word(value = "KALÇA"), Word(value = "LAÇKA")
            ),
            difficulty = Difficulty.ThirdLevel,
            subDifficulty = SubDifficulty.SixthLevel
        )
    )
}