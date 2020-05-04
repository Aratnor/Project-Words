package com.lambadam.projectwords

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import com.lambadam.projectwords.basecontract.FragmentOnBackPressed
import com.lambadam.projectwords.home.HomeFragment
import com.lambadam.projectwords.login.LoginFragment
import com.lambadam.projectwords.models.Difficulty
import com.lambadam.projectwords.models.HighScore
import com.lambadam.projectwords.scores.ScoresFragment
import com.lambadam.projectwords.util.addFragment
import com.lambadam.projectwords.util.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Uygulama Single Activity olarak çalışıyor icinde ki fragmentları degistirerek ekranları değiştiriyoruz
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment()

        setUpNavigationView()

        setToolbar()
    }

    private fun setFragment() {
        val userName = BaseApplication.INSTANCE.getPreferencesManager().getUserName()
        // Kullanıcı adı girilmemisse kullanıcı adını giricegi fragment gösterilir
        if(userName.isNotEmpty()) {
            addFragment(supportFragmentManager, HomeFragment())
        } else {
            addFragment(supportFragmentManager, LoginFragment())
        }

    }

    /**
     * NavigationView -> soldan sağa kaydırdığımızda acılan layout
     * icindeki menu itemlarina tıkladığında ne yapılacagı burda karar veriyoruz
     */
    private fun setUpNavigationView() {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.scores_navigation_menu_item -> replaceFragment(supportFragmentManager,ScoresFragment())
            }

            it.isChecked = true
            drawer_layout.closeDrawers()

            true
        }
    }

    /**
     *  Toolbar(Uygulamanın üstündeki bar) set ediyoruz
     */
    private fun setToolbar() {
        setSupportActionBar(toolbar)
    }

    /**
     * Uygulamada geri tuşuna bastığınızda bu method cagrılır
     */
    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if(fragment is FragmentOnBackPressed) {
            (fragment as? FragmentOnBackPressed)?.onBackPressed()?.not()?.let {
                if(it) {
                    super.onBackPressed()
                }
            }
        } else {
            if (supportFragmentManager.backStackEntryCount > 1) {
                //Go back to previous Fragment
                supportFragmentManager.popBackStackImmediate();
            } else {
                finish()
            }
        }
    }

}
