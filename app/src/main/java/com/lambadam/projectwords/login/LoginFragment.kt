package com.lambadam.projectwords.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.lambadam.projectwords.R
import com.lambadam.projectwords.playscene.PlaySceneFragment
import com.lambadam.projectwords.util.addFragment
import com.lambadam.projectwords.util.replaceFragment
import com.lambadam.projectwords.util.showDialog
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Kullanıcı Adını girdiğimiz Fragment
 */
class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        btApply.setOnClickListener {
            if(viewModel.isValidUserName(etUserName.text.toString())){
                viewModel.addUserNameToSharedPref(etUserName.text.toString())
                replaceFragment(requireActivity().supportFragmentManager,PlaySceneFragment())
            } else {
                showDialog("Lütfen kullanıcı adınızı kontrol edin","Kullanıcı adınız 3 karakterden fazla olmalı") {}
            }
        }
    }

}
