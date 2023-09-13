package umbjm.ft.inf.mydigitalprinting.produk.opsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityOpsiIdCardBinding
import umbjm.ft.inf.mydigitalprinting.produk.cetak.CetakidcardActivity
import umbjm.ft.inf.mydigitalprinting.produk.idcard.SpecidcardActivity

class OpsiidcardActivity : AppCompatActivity() {

    private lateinit var jasaId:Button
    private lateinit var cetakId:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opsi_id_card)

        jasaId = findViewById(R.id.jasaidcard)
        jasaId.setOnClickListener {
            val intent = Intent(this, SpecidcardActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        cetakId = findViewById(R.id.cetakidcard)
        cetakId.setOnClickListener {
            val intent = Intent(this, CetakidcardActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

    }
}