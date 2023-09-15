package umbjm.ft.inf.mydigitalprinting.produk.banner

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
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsipesananActivity
class BannerActivity : AppCompatActivity(), BannerAdapter.BannerItemClickListener {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var bannerItem: ArrayList<BannerItem>

    @JvmField
    val bannerItemClickListener = this  // Menyimpan referensi ke BannerActivity sebagai BannerItemClickListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

        recyclerView = findViewById(R.id.rvBanner)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)
        bannerItem = arrayListOf<BannerItem>()
        getData()



    }


    private fun getData() {
        database =
            FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Products")
        database.child("Banner").orderByChild("idBanner")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        bannerItem.clear()  // Membersihkan list terlebih dahulu
                        for (itemProduk in snapshot.children) {
                            val item = itemProduk.getValue(BannerItem::class.java)
                            bannerItem.add(item!!)
                        }
                        recyclerView.adapter = BannerAdapter(bannerItem, bannerItemClickListener)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Error handling
                }

            })
    }

    override fun onItemClick(idBanner: String, harga: String) {
        // Di sini, Anda dapat membuat Intent untuk berpindah ke aktivitas lain
        // dan membawa ID sebagai data tambahan (extra) dalam Intent.

        val intent = Intent(this, OpsipesananActivity::class.java)
        intent.putExtra("idBanner", idBanner)
        intent.putExtra("harga", harga)
        startActivity(intent)
    }

}
