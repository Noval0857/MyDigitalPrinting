package umbjm.ft.inf.mydigitalprinting.produk.cetak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsibrosurActivity

private lateinit var btnKembali:MaterialButton

class CetakbrosurActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cetakbrosur)

        btnKembali = findViewById(R.id.btnKembali)
        btnKembali.setOnClickListener {
            val intent = Intent(this, OpsibrosurActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }
    }
}