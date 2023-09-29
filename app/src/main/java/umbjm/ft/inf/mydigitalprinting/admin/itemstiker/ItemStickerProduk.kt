package umbjm.ft.inf.mydigitalprinting.admin.itemstiker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.admin.itemidcard.IdcardItem
import umbjm.ft.inf.mydigitalprinting.admin.itemidcard.ItemIdcardAdapter

class ItemStickerProduk : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var stickerItem: ArrayList<StickerItem>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_sticker_produk)

        recyclerView = findViewById(R.id.rv_sticker)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()
        stickerItem = arrayListOf<StickerItem>()
        getData()
    }

    private fun getData() {
        database = FirebaseDatabase
            .getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Products/Sticker")
        database.orderByChild("idSticker")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        stickerItem.clear()
                        for (itemProduk in snapshot.children) {
                            val item = itemProduk.getValue(StickerItem::class.java)
                            stickerItem.add(item!!)
                        }
                        recyclerView.adapter = ItemStickerAdapter(stickerItem)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Error handling
                }
            })
    }
}