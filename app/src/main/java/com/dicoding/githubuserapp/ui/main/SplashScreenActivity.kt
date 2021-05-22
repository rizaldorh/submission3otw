package com.dicoding.githubuserapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.dicoding.githubuserapp.R

class SplashScreenActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Thread.sleep(2000)
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()},2000)


    }
}