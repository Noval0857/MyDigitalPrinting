package umbjm.ft.inf.mydigitalprinting.produk.opsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.spesifikasi.SpecdesainActivity

class OpsibrosurActivity : AppCompatActivity() {

    private lateinit var jasabrosur:ImageView
    private lateinit var cetakbrosur:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opsibrosur)

        jasabrosur = findViewById(R.id.jasabrosur)
        jasabrosur.setOnClickListener {
            val intent = Intent(this, SpecdesainActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

    }
}