package com.lambadam.projectwords.playscene

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lambadam.projectwords.R
import com.lambadam.projectwords.models.AddWord
import com.lambadam.projectwords.models.LevelLetters
import com.lambadam.projectwords.playscene.adapters.LetterAdapter
import com.lambadam.projectwords.playscene.adapters.WordsAdapter
import com.lambadam.projectwords.playscene.contract.OnAdapterUpdate
import com.lambadam.projectwords.util.GameUtil
import com.lambadam.projectwords.util.showDialog
import kotlinx.android.synthetic.main.fragment_play_scene.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Oyun Ekranı
 */
class PlaySceneFragment : Fragment(),
    OnAdapterUpdate {
    private lateinit var viewModel: PlaySceneViewModel

    private var wordsAdapter = WordsAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_scene, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlaySceneViewModel::class.java)

        viewModel.initAdapter()

        setWordsAdapter()

        setViewModelObserver()

        viewModel.startTimer()

        checkIsGameContinue()
    }

    private fun checkIsGameContinue() {
        if(arguments != null) {

            val isContinue = requireArguments().getBoolean("isContinueGame")
            if (isContinue) viewModel.loadCurrentState()
        }
    }

    /**
     * Oyun icindeki değişkenleri statelerini observe ettiğimiz yer
     * LiveData yapısı icinde tutuğu değişkenin değeri değiştiriğinde
     * o değişikliği observe ettiğimiz ve bu değişime göre gerekli değişikliği
     * yapmamıza olanak sağlayan bir yapı
     * (Observer Pattern: https://en.wikipedia.org/wiki/Observer_pattern )
     */
    private fun setViewModelObserver() {
        viewModel.changedLetterState.observe(viewLifecycleOwner, Observer {
            tvCurrentWord.text = it
        })

        viewModel.wordEnteredState.observe(viewLifecycleOwner, Observer {
            if(!it.isReEnteredWord && it.isWordExists) {
                viewModel.updateWordsAdapter()
            }
            setAlertDialog(it)

        })

        viewModel.setLetterAdapter.observe(viewLifecycleOwner,
            Observer { setLetterAdapter(it) })
        viewModel.resetWordAdapterState.observe(viewLifecycleOwner,
            Observer { setWordsAdapter(it) })

        viewModel.changedLetterState.observe(viewLifecycleOwner,
            Observer { tvCurrentWord.text = it })

        viewModel.difficultyState.observe(viewLifecycleOwner,
            Observer { currentLevel.text = it })
        viewModel.subDifficultyState.observe(viewLifecycleOwner,
            Observer { currentSubLevel.text = it })

        viewModel.timerState.observe(viewLifecycleOwner,
            Observer { timer.text = String.format("%s %s","Time:",it) })

        viewModel.totalScoreState.observe(viewLifecycleOwner,
            Observer { totalScore.text = String.format("%s %s","Total Score:",it) })
        viewModel.currentScoreState.observe(viewLifecycleOwner,
            Observer { currentScore.text = String.format("%s %s","Level Score:", it) })

        viewModel.remainingWordState.observe(viewLifecycleOwner,
            Observer { remainingWords.text = String.format("%s %s", "Remaining Words :", it)})

        viewModel.isNewHighScoreState.observe(viewLifecycleOwner, Observer {
            showDialog(
                "New High Score",
                String.format("%s %s %s %s %s %s"
                    ,"Your Score: ",it.score,
                    "\n Position:",it.position,
                    "\n Difficulty:", GameUtil.getDifficultyPrettyFormat(it.difficulty.toString()))) {}
        })

        viewModel.gameEndState.observe(viewLifecycleOwner,
        Observer {
            showDialog(
                "Tüm Levelleri bitirdiniz",
                String.format("%s %s %s %s %s %s %s",
                 "First Level score: ", it[0],
                 "\nSecond Level score: ", it[1],
                 "\nThird Level score: ", it[2],
                 "\nTebrikler oyunu bitirdiniz")
                ) {
                findNavController().navigate(R.id.play_scene_action_to_home)
            }
        })

    }

    /**
     * Olasılıklar
     * Kelime Yanlış Kelimedir
     * Kelime Doğru kelimedir ama daha önceden girilmiştir
     * Kelime Doğru kelimedir ama daha önceden girilmemistir
     */
    override fun onFinish(isWordFound: Boolean,word: String) {
        viewModel.onWordEntered(isWordFound,word)


    }

    /**
     * Kullanıcı Kelime girdiğinde Kelimenin doğru ve ya yanlış olduğunu gösterdiğimiz popup
     *
     */
    private fun setAlertDialog(word: AddWord) {
        val title =
            if (!word.isWordExists) "Başarısız deneme"
            else if(word.isReEnteredWord) "Başarısınız deneme"
            else "Doğru Kelimeyi Buldunuz"
        val message =
            if (!word.isWordExists) "Başarısız deneme"
            else if(word.isReEnteredWord) "Aynı Kelimeyi Tekrar girdiniz"
            else "Kelime Buldunuz"
        showAlertDialog(title,message, word.isLevelChanged)
    }

    private fun showAlertDialog(title: String, body: String, isLevelChanged: Boolean = false) {
       showDialog(title,body) {
           if(isLevelChanged) {
               viewModel.updateWordsAdapter()
           }
           viewModel.setLetterAdapter()
           SimpleDateFormat("s")
       }
    }

    // Kullanıcı Harf sectiğinde calısan method
    override fun onSelectedChar(value: String) {
        viewModel.changeLetterState(value.toUpperCase(Locale.getDefault()))
    }

    //Kullanıcı Sectigi harfi secmeyi kaldırdığında calısan method
    override fun deselectedChar(value: String) {
        viewModel.changeLetterState(value.toUpperCase(Locale.getDefault()))
    }

    private fun setLetterAdapter(levelLetter: LevelLetters) {
        val letterAdapter =
            LetterAdapter(
                levelLetter,
                this
            )
        rvLetter.adapter = letterAdapter
        rvLetter.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        tvCurrentWord.text = ""
    }



    private fun setWordsAdapter() {
        wordsAdapter =
            WordsAdapter()
        rvWord.adapter = wordsAdapter
        rvWord.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun setWordsAdapter(list: List<String>) {
        wordsAdapter =
            WordsAdapter(list)
        rvWord.adapter = wordsAdapter
        rvWord.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveCurrentState()
    }

}
