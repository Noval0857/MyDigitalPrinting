package umbjm.ft.inf.mydigitalprinting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import umbjm.ft.inf.mydigitalprinting.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 3000 // Durasi splash screen dalam milidetik (3 detik)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Menggunakan Handler untuk menunda pindah ke aktivitas login
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Menutup aktivitas splash screen
        }, splashTimeOut)
    }
}