package umbjm.ft.inf.mydigitalprinting.produk.opsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.cetak.CetakstikerActivity
import umbjm.ft.inf.mydigitalprinting.produk.spesifikasi.SpecstickerActivity

class OpsistickerActivity : AppCompatActivity() {

    private lateinit var jasaSticker: ImageView
    private lateinit var jasaDesain: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opsi_stiker)

        jasaSticker = findViewById(R.id.jasaSticker)
        jasaSticker.setOnClickListener {
            val intent = Intent(this, SpecstickerActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        jasaDesain = findViewById(R.id.jasaDesain)
        jasaDesain.setOnClickListener {
            val intent = Intent(this, CetakstikerActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

    }
}