package umbjm.ft.inf.mydigitalprinting.produk.idcard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsiidcardActivity

class IdCardActivity : AppCompatActivity(), IdCardAdapter.IdCardItemClickListener {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var idcardItem: ArrayList<IdCardItem>

    @JvmField
    val idcardItemClickListener = this  // Menyimpan referensi ke BannerActivity sebagai BannerItemClickListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_card)

        recyclerView = findViewById(R.id.rvIdcard)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)
        idcardItem = arrayListOf<IdCardItem>()
        getData()



    }


    private fun getData() {
        database =
            FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Products")
        database.child("Idcard").orderByChild("idCard")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        idcardItem.clear()  // Membersihkan list terlebih dahulu
                        for (itemProduk in snapshot.children) {
                            val item = itemProduk.getValue(IdCardItem::class.java)
                            idcardItem.add(item!!)
                        }
                        recyclerView.adapter = IdCardAdapter(idcardItem, idcardItemClickListener)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Error handling
                }

            })
    }

    override fun onItemClick(idCard: String, harga: String) {
        // Di sini, Anda dapat membuat Intent untuk berpindah ke aktivitas lain
        // dan membawa ID sebagai data tambahan (extra) dalam Intent.

        val intent = Intent(this, OpsiidcardActivity::class.java)
        intent.putExtra("idCard", idCard)
        intent.putExtra("harga", harga)
        startActivity(intent)
    }

}