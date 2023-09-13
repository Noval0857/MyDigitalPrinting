package umbjm.ft.inf.mydigitalprinting.produk.cetak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsipesananActivity

class CetakdesainActivity : AppCompatActivity() {

    private lateinit var pesan:MaterialButton
    private lateinit var btnKembali:MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cetak)

        btnKembali = findViewById(R.id.btnKembali)
        btnKembali.setOnClickListener {
            val intent = Intent(this, OpsipesananActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        pesan = findViewById(R.id.btnPesan)

//        bayar.setOnClickListener {
//            val intent = Intent(this, PembayaranActivity::class.java)
//            startActivity(intent)
//        }
    }
}