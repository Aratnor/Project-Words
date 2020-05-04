package com.lambadam.projectwords

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.lambadam.projectwords.util.PreferencesManager

/**
 * Application Classı uygulamanın calıstığı süre boyunca calısan bir sınıftır,
 * uygulama genelinde ihtiyacımız olan android
 * context(Android kaynaklarına ulasmak icin uygulamanın bize provide ettigi bir yapı) bağımlı
 * olan değişkenleri provide ettigimiz yer
 */
class BaseApplication: Application() {

    /** Android cihazda local store olarak kullandıgımız yapı( kücük ve primitive,String yapılar icin kullanılır) **/
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var preferencesManager: PreferencesManager

    /**
     * İlk olarak application onCreate calısır
     * daha sonra cagırdıgımız activity ve fragmentların onCreate calısır
     */
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initializePreferencesManager()
    }

    /**
     * Javadaki Static degiskenlere karsılık gelen yapı
     *
     */
    companion object {
        /**
         * Bu Application Sınıfına dışardan singleton yapıda ulaşmak icin kullandığımız yapı
         */
        lateinit var INSTANCE: BaseApplication
            private set
    }

    fun getPreferencesManager(): PreferencesManager {
        initializePreferencesManager()

        return preferencesManager
    }

    private fun initializePreferencesManager(): Boolean {
        val isInitialized = initializePreferences()
        if(!this::preferencesManager.isInitialized) {
            if (isInitialized)
                preferencesManager = PreferencesManager(sharedPreferences)
        }
        return this::preferencesManager.isInitialized
    }


    private fun initializePreferences(): Boolean {
        if(!this::sharedPreferences.isInitialized) {
            sharedPreferences = getSharedPreferences( "com.lambadam.projectwords", Context.MODE_PRIVATE )
        }

        return this::sharedPreferences.isInitialized
    }


}