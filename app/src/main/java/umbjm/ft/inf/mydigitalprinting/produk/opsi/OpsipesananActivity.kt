package umbjm.ft.inf.mydigitalprinting.produk.opsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.cetak.CetakdesainActivity
import umbjm.ft.inf.mydigitalprinting.produk.spesifikasi.SpecdesainActivity

class OpsipesananActivity : AppCompatActivity() {

    private lateinit var jasadesain: ImageView
    private lateinit var kirimdesain: ImageView
    private var jasaHarga = 25000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opsipesanan)

        jasadesain = findViewById(R.id.jasadesain)
        kirimdesain = findViewById(R.id.kirimdesain)

        val idBanner = intent.getStringExtra("idBanner")
        val harga = intent.getStringExtra("harga")
        val biayaDesain = jasaHarga.toString()

        jasadesain.setOnClickListener {
            val intent = Intent(this, SpecdesainActivity::class.java)
            intent.putExtra("idBanner", idBanner)
            intent.putExtra("biayaDesain", biayaDesain)
            startActivity(intent)
        }

        kirimdesain.setOnClickListener {
            val intent = Intent(this, CetakdesainActivity::class.java)
            intent.putExtra("idBanner", idBanner)
            intent.putExtra("harga", harga)
            startActivity(intent)
        }


    }
}