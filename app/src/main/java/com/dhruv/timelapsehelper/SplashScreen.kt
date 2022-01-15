package com.dhruv.timelapsehelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val startAct = Intent(this,
                MainActivity::class.java)
            startActivity(startAct)
        },3000)

    }

    override fun onPause() {
        finish()
        super.onPause()
    }
}