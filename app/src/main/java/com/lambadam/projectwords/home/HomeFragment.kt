package com.lambadam.projectwords.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.lambadam.projectwords.BaseApplication

import com.lambadam.projectwords.R
import com.lambadam.projectwords.login.LoginFragment
import com.lambadam.projectwords.playscene.PlaySceneFragment
import com.lambadam.projectwords.util.replaceFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Ana menün cıktığı fragment
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonClicks()

        checkGameState()
    }

    /**
     * Daha önce ortasında cıkılan oyun var ise bu oyunun state olup olmadığı kontrol edilir
     * eger böyle bir state varsa Devam Et buttonu görünür
     */
    private fun checkGameState() {
        val isGameStateSaved = BaseApplication.INSTANCE.getPreferencesManager().isCurrentGameStateSaved()
        if(isGameStateSaved) {
            btnContinue.visibility = View.VISIBLE
        } else
            btnContinue.visibility = View.GONE
    }

    /**
     * Butonlara tıklandığında ne yapılacağına burda karar veriyoruz
     */
    private fun setButtonClicks() {
        btnChangeUserName.setOnClickListener {
            findNavController().navigate(R.id.home_action_to_login)
        }
        btnContinue.setOnClickListener {
            val bundle = bundleOf("isContinueGame" to true)
            findNavController().navigate(R.id.home_action_to_play_scene,bundle)
        }
        btnNewGame.setOnClickListener {
            findNavController().navigate(R.id.home_action_to_play_scene)
        }
    }

}
