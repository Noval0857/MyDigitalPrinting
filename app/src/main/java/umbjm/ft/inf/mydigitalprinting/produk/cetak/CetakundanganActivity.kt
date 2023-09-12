package umbjm.ft.inf.mydigitalprinting.produk.cetak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import umbjm.ft.inf.mydigitalprinting.R

class CetakundanganActivity : AppCompatActivity() {

    private lateinit var bayar:MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cetakundangan)

        bayar = findViewById(R.id.btnS1)

//        bayar.setOnClickListener {
//            val intent = Intent(this, PembayaranActivity::class.java)
//            startActivity(intent)
//        }
    }
}