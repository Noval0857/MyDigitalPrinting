package umbjm.ft.inf.mydigitalprinting.profil

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAboutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvcontact.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }
    }
}