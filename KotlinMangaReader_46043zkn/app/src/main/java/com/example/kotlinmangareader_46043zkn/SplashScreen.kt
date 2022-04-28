package com.example.kotlinmangareader_46043zkn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler() .postDelayed({
            startActivity(Intent(this@SplashScreen,MainActivity::class.java))
        }, 3000) // Delay 3 seconds
    }
}
