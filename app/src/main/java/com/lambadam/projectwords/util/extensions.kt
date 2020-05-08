package com.lambadam.projectwords.util

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Extension Function bir kotlin özelligi. Bu özellik icerigini degistiremeyecegiz Kütüphanelerdeki sınıflara
 * yeni methodlar yazmamızı saglar bu methodlar yazdığımız sınıfın degiskenleri vs icindeki degerlere erişebilir ve
 * bu sınıflardan extend edilen childlar tarafında erisilebilir kod tekrarını büyük ölcüde azalıyor
 *
 * Activity.addFragment(..) bu Activity class ına
 * addFragment methodunu ekler ve Activityler icerisinde bu method calıstırılabilir
 *
 */
fun Activity.addFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    @IdRes containerId: Int
) {
    /**
     * Activity icine fragment ekler
     */
    fragmentManager.beginTransaction().add(containerId,fragment).addToBackStack(null).commit()
}

fun Activity.replaceFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    @IdRes containerId: Int

) {
    fragmentManager.beginTransaction().replace(containerId,fragment).addToBackStack(null).commit()

}
fun Activity.showDialog(
    title: String, body: String, action: () -> Unit
) {
        AlertDialog.Builder(this.baseContext)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(title)
            .setMessage(body)
            .setPositiveButton(
                "Yes"
            ) { dialogInterface, i ->
                dialogInterface.dismiss()
                action.invoke()
            }
            .show()
}

/**
 * Ekranda cıkan popUpları burda oluşturuyoruz
 */
fun Fragment.showDialog(
    title: String, body: String, action: () -> Unit
) {
    AlertDialog.Builder(this.context)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle(title)
        .setMessage(body)
        .setPositiveButton(
            "Yes"
        ) { dialogInterface, i ->
            dialogInterface.dismiss()
            action.invoke()
        }
        .setCancelable(false)
        .show()
}

fun Fragment.addFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    @IdRes containerId: Int
) {
    fragmentManager.beginTransaction().add(containerId,fragment).addToBackStack(null).commit()
}

fun Fragment.replaceFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    @IdRes containerId: Int

) {
    fragmentManager.beginTransaction().replace(containerId ,fragment).addToBackStack(null).commit()
}

fun Fragment.replaceFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    isContinue: Boolean,
    @IdRes containerId: Int

) {
    val bundle = Bundle()
     bundle.putBoolean("isContinue", isContinue)
    fragment.arguments = bundle
    fragmentManager.beginTransaction().replace(containerId ,fragment).addToBackStack(null).commit()
}





