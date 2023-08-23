package umbjm.ft.inf.mydigitalprinting.produk.opsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.cetak.CetakundanganActivity
import umbjm.ft.inf.mydigitalprinting.produk.spesifikasi.SpecundanganActivity
import umbjm.ft.inf.mydigitalprinting.produk.spesifikasi.SpesifikasisertifikatActivity

class OpsisertifikatActivity : AppCompatActivity() {

    private lateinit var jasadesain: ImageView
    private lateinit var kirimdesain: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opsi_sertifikat)

        jasadesain = findViewById(R.id.jasadesain)
        kirimdesain = findViewById(R.id.kirimdesain)

        jasadesain.setOnClickListener {
            val intent = Intent(this, SpesifikasisertifikatActivity::class.java)
            startActivity(intent)
        }

        kirimdesain.setOnClickListener {
            val intent = Intent(this, CetakundanganActivity::class.java)
            startActivity(intent)
        }

    }
}