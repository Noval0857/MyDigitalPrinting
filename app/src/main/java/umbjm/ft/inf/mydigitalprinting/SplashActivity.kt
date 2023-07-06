package umbjm.ft.inf.mydigitalprinting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import umbjm.ft.inf.mydigitalprinting.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Menyembunyikan Action
        supportActionBar?.hide()

        Handler().postDelayed({
            //Menjalankan MainActivity
            startActivity(Intent(this, LoginActivity::class.java))
            //Menghentikan SplashActivity
            finish()
        }, 1000) //set Durasi Splash Screen
    }
}