package umbjm.ft.inf.mydigitalprinting.produk.sertifikat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsisertifikatActivity

class SertifikatActivity : AppCompatActivity(), SertifikatAdapter.SertifikatItemClickListener {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var sertifikatItem: ArrayList<SertifikatItem>

    @JvmField
    val sertifikatItemClickListener = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sertifikat)

        recyclerView = findViewById(R.id.rvSertifikat)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)
        sertifikatItem = arrayListOf<SertifikatItem>()
        getData()
    }

    private fun getData() {
        database =
            FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Products")
        database.child("Sertifikat").orderByChild("idSertifikat")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        sertifikatItem.clear()  // Membersihkan list terlebih dahulu
                        for (itemProduk in snapshot.children) {
                            val item = itemProduk.getValue(SertifikatItem::class.java)
                            sertifikatItem.add(item!!)
                        }
                        recyclerView.adapter = SertifikatAdapter(sertifikatItem, sertifikatItemClickListener)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Error handling
                }

            })
    }

    override fun onItemClick(idSertifikat: String, harga :String) {
        // Di sini, Anda dapat membuat Intent untuk berpindah ke aktivitas lain
        // dan membawa ID sebagai data tambahan (extra) dalam Intent.

        val intent = Intent(this, OpsisertifikatActivity::class.java)
        intent.putExtra("idSertifikat", idSertifikat)
        intent.putExtra("harga", harga)
        startActivity(intent)
    }
}