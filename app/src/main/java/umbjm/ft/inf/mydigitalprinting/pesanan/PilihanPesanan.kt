package umbjm.ft.inf.mydigitalprinting.pesanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import umbjm.ft.inf.mydigitalprinting.R

class PilihanPesanan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilihan_pesanan)

        val resi = findViewById<Button>(R.id.resi)
        resi.setOnClickListener {
            startActivity(Intent(this, PesananActivity::class.java))
        }
        val desainAnda = findViewById<Button>(R.id.DesainAnda)
        desainAnda.setOnClickListener {
            startActivity(Intent(this, DesainAnda::class.java))
        }
    }
}