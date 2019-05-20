package com.sumitthakur.walmartproductlist.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sumitthakur.walmartproductlist.R
import kotlinx.android.synthetic.main.splash_activity.*

/**
 * space to advertise
 * @author Sumit.T
 */

class SplashActivity: AppCompatActivity() {
    private val SPLASH_TIME = 2000L
    private val DELAY_TIME = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        splashContainer.setAlpha(0f)
        splashContainer.animate().setDuration(SPLASH_TIME).setStartDelay(DELAY_TIME).alpha(1f).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                goToHome()
            }
        })
    }

    private fun goToHome() {
        splashContainer.setAlpha(1f)
        splashContainer.animate().setDuration(DELAY_TIME).setStartDelay(SPLASH_TIME).alpha(0f).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        })
    }
}