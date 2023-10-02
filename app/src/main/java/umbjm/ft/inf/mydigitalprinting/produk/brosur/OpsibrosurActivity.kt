package umbjm.ft.inf.mydigitalprinting.produk.brosur

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import umbjm.ft.inf.mydigitalprinting.R

class OpsibrosurActivity : AppCompatActivity() {

    private lateinit var jasabrosur:ImageView
    private lateinit var cetakbrosur:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opsibrosur)

        jasabrosur = findViewById(R.id.jasabrosur)

        val idBrosur = intent.getStringExtra("idBrosur")
        val harga = intent.getStringExtra("harga")

        jasabrosur.setOnClickListener {
            val intent = Intent(this, SpecbrosurActivity::class.java)
            intent.putExtra("idBrosur", idBrosur)
            intent.putExtra("harga", harga)
            startActivity(intent)
            return@setOnClickListener
        }

        cetakbrosur = findViewById(R.id.cetakbrosur)
        cetakbrosur.setOnClickListener {
            val intent = Intent(this, CetakbrosurActivity::class.java)
            intent.putExtra("idBrosur", idBrosur)
            startActivity(intent)
            return@setOnClickListener
        }

    }
}