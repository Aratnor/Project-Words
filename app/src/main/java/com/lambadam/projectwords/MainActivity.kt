package com.lambadam.projectwords

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
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
        
        setSideNavView()

        setToolbar()
    }

    private fun setFragment() {
        val userName = BaseApplication.INSTANCE.getPreferencesManager().getUserName()
        // Kullanıcı adı girilmemisse kullanıcı adını giricegi fragment gösterilir
        if(userName.isNotEmpty()) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.home_dest)
        } else {
            findNavController(R.id.nav_host_fragment).navigate(R.id.login_dest)
        }

    }

    private fun setSideNavView() {
       nav_view?.setupWithNavController(findNavController(R.id.nav_host_fragment))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(item)
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
        val navHostFragment = supportFragmentManager.primaryNavigationFragment
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0);
         if(fragment is FragmentOnBackPressed) {
            (fragment as? FragmentOnBackPressed)?.onBackPressed()?.not()?.let {
                if(it) {
                    findNavController(R.id.nav_host_fragment).popBackStack()
                }
            }
        } else {
            findNavController(R.id.nav_host_fragment).popBackStack()
        }
    }

}
