package umbjm.ft.inf.mydigitalprinting.profil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityContactBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}