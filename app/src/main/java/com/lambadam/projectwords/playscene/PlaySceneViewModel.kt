package com.lambadam.projectwords.playscene

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambadam.projectwords.BaseApplication
import com.lambadam.projectwords.models.*
import com.lambadam.projectwords.provideLettersLevelOne
import com.lambadam.projectwords.provideLettersLevelThree
import com.lambadam.projectwords.provideLettersLevelTwo
import com.lambadam.projectwords.util.GameUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 *Oyun Ekranındaki Logic islerini yaptığımız viewModel
 */
class PlaySceneViewModel: ViewModel() {
    //Kullanıcı harf secimi veya kaldırmasını izlediğimiz live data
    val changedLetterState = MutableLiveData<String>()

    //Kullanıcı Kelime girdiğinde
    val wordEnteredState = MutableLiveData<AddWord>()

    //Oyun Alt zorluğu veya zorluğu değiştiğinde harflerin değişimini takip eder
    val setLetterAdapter = MutableLiveData<LevelLetters>()

    val gameEndState = MutableLiveData<List<String >>()

    //Bulunan kelimelerin ekran gösterilmesi takip eder
    val resetWordAdapterState = MutableLiveData<List<String>>()

    val difficultyState = MutableLiveData<String>()

    val subDifficultyState = MutableLiveData<String>()

    //Oyun ekranındaki zamanı takip eder
    val timerState = MutableLiveData<Int>(0)

    //Kullanıcının o levelde kazandığı toplam skor
    val totalScoreState = MutableLiveData<Int>()

    //Kullanıcının bulunduğu alt levelde kazanabileceği skor
    val currentScoreState = MutableLiveData<Int>()

    //O alt zorlukta kac kelime kaldığını gösterir
    val remainingWordState = MutableLiveData<Int>()

    //Kullanıcı yeni yüksek skor yapıp yapmadığını takip eder
    val isNewHighScoreState = MutableLiveData<NewHighScore>()

    //Kullanıcın o alt levelde bulduğu kelimeler
    private val foundedWords = mutableListOf<String>()

    // suan ki alt leveldeki harfler kelimeler vs bilgileri
    private var levelLetters = provideLettersLevelOne()

    // alt thread de calısan zamanı göstermeye yarıyan RxJava disposable
    private lateinit var disposableTimer: Disposable

    //Yeni alt levele gecmemize yarıyan değişken
    private var currentSubLevel: Int = 0

    //Yeni levele gecmemize yarıyan değişken
    private var currentLevel: Int = 0

    private var scores: MutableList<String> = mutableListOf()

    //Yarıda kalmıs oyunun hangi zamanda kaldığını tutuğumuz değişken
    private var savedTimeState : Int = 0

    //Toplam gecen zaman
    private var currentTime: Int = 0

    //Toplam başarısız kelime girişimi
    private var failedAttempt: Int = 0

    fun changeLetterState(typed: String) {
        changedLetterState.value = typed
    }

    fun setLetterAdapter() {
        if(currentSubLevel == 0) setLevelStates()
        if(currentSubLevel == 6) {
            currentSubLevel = 0

            //Kullanıcı yeni difficulty(level gectiğini gösterir
            provideNextLevel()
        }
        setLetterAdapter.value = levelLetters[currentSubLevel]

        updateCurrentScore()
    }

    fun initAdapter() {
        setLetterAdapter()
    }

    /**
     * Kullanıcı kelime girdiğinde oyun icindeki gerekli değişiklikleri
     * yaptığımız yer
     */
    fun onWordEntered(isWordFound: Boolean,word: String) {
        if(!isWordFound) failedAttempt++
        else if(isWordFound && !isWordEntered(word) ) {
            if(remainingWordState.value != null)
            remainingWordState.value = remainingWordState.value!! - 1
        }

        var isLevelChanged = false
        val isFound = if (isWordFound && !isWordEntered(word)) {
            foundedWords.add(word)
            if (foundedWords.size == levelLetters[currentSubLevel].words.size) {
                updateTotalScore()
                currentSubLevel++
                foundedWords.clear()
                setLetterAdapter()
                updateWordsAdapter()
                isLevelChanged = true

                remainingWordState.value = levelLetters[currentSubLevel].words.size
            }
            true
        } else {
            updateCurrentScore()
            false
        }


        if (isLevelChanged) {
            setLevelStates()
        }

        wordEnteredState.value = AddWord(word, !isFound, isWordFound, isLevelChanged)
    }

    private fun updateTotalScore() {
        val score =  calculateScore()
        if (totalScoreState.value != null)
            totalScoreState.value = totalScoreState.value!! + score
        else
            totalScoreState.value = score
    }

    private fun updateCurrentScore() {
        currentScoreState.value = calculateScore()
    }

    private fun calculateScore(): Int {
        val timeValue: Long = if(timerState.value == null) 1L else timerState.value!!.toLong()
        return GameUtil.calculateScore(
            timeValue,
            failedAttempt,
            levelLetters[currentSubLevel].words.size,
            GameUtil.getWordLengthByDifficulty(levelLetters[currentSubLevel].difficulty),
            foundedWords.size
        )
    }

    /**
     * Level-altlevel değiştiğinde bu method cağrılır
     */
    private fun setLevelStates() {
        val level = levelLetters[currentSubLevel].difficulty.toString()
        val levelNumber = level.substring(0,level.length-5)
        val levelString = level.substring(level.length-5,level.length)

        val subLevel = levelLetters[currentSubLevel].subDifficulty.toString()
        val subLevelNumber = subLevel.substring(0,subLevel.length-5)
        val subLevelString = subLevel.substring(subLevel.length-5, subLevel.length)

        difficultyState.value = "" +
                "$levelNumber $levelString  (Length : " +
                "${GameUtil.getWordLengthByDifficulty(levelLetters[currentSubLevel].difficulty)} letter)"
        subDifficultyState.value = "Sub Level : $subLevelNumber $subLevelString"

        remainingWordState.value = levelLetters[currentSubLevel].words.size

    }
    private fun isWordEntered(enteredWord: String): Boolean {
        if(foundedWords.isEmpty()) return false
        for(word in foundedWords) {
            if(enteredWord == word) return true
        }
        return false
    }

    fun updateWordsAdapter() {
        resetWordAdapterState.value = foundedWords
    }

    private fun provideNextLevel() {
        if(totalScoreState.value != null) {
            val list =
                BaseApplication.INSTANCE
                    .getPreferencesManager()
                    .getAllHighestScores(levelLetters[currentSubLevel].difficulty)
                    .toMutableList()

            list.add(HighScore(
                totalScoreState.value!!,
                BaseApplication.INSTANCE.getPreferencesManager().getUserName()))
            val sortedList = GameUtil.sortHighestScores(list)
            val positionOfScore = GameUtil.getPositionOfScore(sortedList,totalScoreState.value!!)
            if(positionOfScore != -1) isNewHighScoreState.value = NewHighScore(totalScoreState.value!!,positionOfScore,levelLetters[currentSubLevel].difficulty)
            BaseApplication.INSTANCE
                .getPreferencesManager()
                .updateHighestScores(
                    sortedList,
                    levelLetters[currentSubLevel].difficulty)

            scores.add(totalScoreState.value.toString())

            totalScoreState.value = 0
        }
        if(currentLevel == 2) {
            gameEndState.value = scores
        } else currentLevel++
        provideRepository()
        resetTimer()

    }
    private fun provideRepository() {
        when(currentLevel) {
            0 -> levelLetters = provideLettersLevelOne()
            1 -> levelLetters = provideLettersLevelTwo()
            2 -> levelLetters = provideLettersLevelThree()
        }
    }

    /**
     * Kullanıcı oyunun yarısında cıktığında oyunun durumunu kaydettiğimiz method
     */
     fun saveCurrentState() {
        val currentGameState = CurrentGameState(
            currentLevel,
            currentSubLevel,
            foundedWords,
            failedAttempt,
            if(currentScoreState.value != null)currentScoreState.value!! else 0,
            if(totalScoreState.value != null)totalScoreState.value!! else 0,
            currentTime)
        BaseApplication.INSTANCE.getPreferencesManager().saveCurrentGameState(currentGameState)
    }

    /**
     * Kullanıcı kaldığı yerden devam etmek isterse oyun durumunu yüklediğimiz method
     */
    fun loadCurrentState() {
        val currentGameState = BaseApplication.INSTANCE.getPreferencesManager().getCurrentGameState()
        if(currentGameState != null) {
            currentLevel = currentGameState.currentLevel
            currentSubLevel = currentGameState.currentSubLevel
            foundedWords.clear()
            foundedWords.addAll(currentGameState.foundedWords)
            failedAttempt = currentGameState.failedAttempt
            currentScoreState.value = currentGameState.currentScore
            totalScoreState.value = currentGameState.totalScore
            savedTimeState = currentGameState.currentTime

            provideRepository()

            setLetterAdapter.value  = levelLetters[currentSubLevel]

            remainingWordState.value = levelLetters[currentSubLevel].words.size - foundedWords.size

            updateWordsAdapter()
        }

    }

    /**
     * Ekranda görünen Zamanı kullandığımız method RxJava interval ile yapılmıştır
     */
    fun startTimer() {
        disposableTimer = Observable
            .interval(1,TimeUnit.SECONDS, Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { time ->
                currentTime = savedTimeState + time.toInt()
                timerState.value = currentTime
                if( time.toInt() % 5 == 0 ) {
                    updateCurrentScore()
                }
            }
    }

    fun resetTimer() {
        if(!disposableTimer.isDisposed) disposableTimer.dispose()
        timerState.value = 0
        startTimer()
    }

    fun stopTimer() {
        if(!disposableTimer.isDisposed) disposableTimer.dispose()
        timerState.value = 0
    }

    //Kullanıcı Fragmenti kapattığında cagrılan method
    override fun onCleared() {
        super.onCleared()
        stopTimer()
        saveCurrentState()
    }

}