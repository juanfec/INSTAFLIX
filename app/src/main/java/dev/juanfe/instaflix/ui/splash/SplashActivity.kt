package dev.juanfe.instaflix.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import dev.juanfe.instaflix.R
import dev.juanfe.instaflix.ui.home.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        splashscreenstart()
    }

    fun splashscreenstart() {
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, duration.toLong())
    }

    companion object {
        //Thats for duration
        var duration = 1000
    }
}
