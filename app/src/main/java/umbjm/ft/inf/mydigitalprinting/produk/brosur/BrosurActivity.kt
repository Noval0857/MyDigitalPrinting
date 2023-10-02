package umbjm.ft.inf.mydigitalprinting.produk.brosur

import android.annotation.SuppressLint
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

class BrosurActivity : AppCompatActivity(), BrosurAdapter.BrosurItemClickListener {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var brosurItem: ArrayList<BrosurItem>

    @JvmField
    val brosurItemClickListener = this  // Menyimpan referensi ke BannerActivity sebagai BannerItemClickListener


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brosur)

        recyclerView = findViewById(R.id.rvBrosur)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)
        brosurItem = arrayListOf<BrosurItem>()
        getData()



    }


    private fun getData() {
        database =
            FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Products")
        database.child("Brosur").orderByChild("idBrosur")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        brosurItem.clear()  // Membersihkan list terlebih dahulu
                        for (itemProduk in snapshot.children) {
                            val item = itemProduk.getValue(BrosurItem::class.java)
                            brosurItem.add(item!!)
                        }
                        recyclerView.adapter = BrosurAdapter(brosurItem, brosurItemClickListener)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Error handling
                }

            })
    }

    override fun onItemClick(idBrosur: String, harga :String) {
        // Di sini, Anda dapat membuat Intent untuk berpindah ke aktivitas lain
        // dan membawa ID sebagai data tambahan (extra) dalam Intent.

        val intent = Intent(this, OpsibrosurActivity::class.java)
        intent.putExtra("idBrosur", idBrosur)
        intent.putExtra("harga", harga)
        startActivity(intent)
    }

}