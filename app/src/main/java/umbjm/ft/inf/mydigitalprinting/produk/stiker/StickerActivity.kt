package umbjm.ft.inf.mydigitalprinting.produk.stiker

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
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsistickerActivity

class StickerActivity : AppCompatActivity(), StickerAdapter.StickerItemClickListener {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var stickerItem: ArrayList<StickerItem>

    @JvmField
    val stickerItemClickListener = this  // Menyimpan referensi ke BannerActivity sebagai BannerItemClickListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticker)

        recyclerView = findViewById(R.id.rvSticker)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)
        stickerItem = arrayListOf<StickerItem>()
        getData()



    }


    private fun getData() {
        database =
            FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Products")
        database.child("Sticker").orderByChild("id")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        stickerItem.clear()  // Membersihkan list terlebih dahulu
                        for (itemProduk in snapshot.children) {
                            val item = itemProduk.getValue(StickerItem::class.java)
                            stickerItem.add(item!!)
                        }
                        recyclerView.adapter = StickerAdapter(stickerItem, stickerItemClickListener)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Error handling
                }

            })
    }

    override fun onItemClick(idSticker: String, harga: String) {
        // Di sini, Anda dapat membuat Intent untuk berpindah ke aktivitas lain
        // dan membawa ID sebagai data tambahan (extra) dalam Intent.

        val intent = Intent(this, OpsistickerActivity::class.java)
        intent.putExtra("idSticker", idSticker)
        intent.putExtra("harga", harga)
        startActivity(intent)
    }

}