package umbjm.ft.inf.mydigitalprinting.produk.opsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.cetak.CetakundanganActivity
import umbjm.ft.inf.mydigitalprinting.produk.spesifikasi.SpecundanganActivity

class OpsiundanganActivity : AppCompatActivity() {

    private lateinit var jasadesain: ImageView
    private lateinit var kirimdesain: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opsi_undangan)

        jasadesain = findViewById(R.id.jasadesain)
        kirimdesain = findViewById(R.id.kirimdesain)

        jasadesain.setOnClickListener {
            val intent = Intent(this, SpecundanganActivity::class.java)
            startActivity(intent)
        }

        kirimdesain.setOnClickListener {
            val intent = Intent(this, CetakundanganActivity::class.java)
            startActivity(intent)
        }
    }
}