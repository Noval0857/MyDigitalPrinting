package umbjm.ft.inf.mydigitalprinting.produk.cetak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityCetakstikerBinding

class CetakstikerActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCetakstikerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCetakstikerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCetak.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }




    }
}