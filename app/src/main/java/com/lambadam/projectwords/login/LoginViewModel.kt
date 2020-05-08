package com.lambadam.projectwords.login

import androidx.lifecycle.ViewModel
import com.lambadam.projectwords.BaseApplication

class LoginViewModel: ViewModel() {

    fun addUserNameToSharedPref(userName: String) {
        BaseApplication.INSTANCE.getPreferencesManager().setUserName(userName)
    }

    fun isValidUserName(userName: String?): Boolean {
        if(userName == null || userName.isEmpty()){
            return false
        }
        return userName.length >= 3
    }

}