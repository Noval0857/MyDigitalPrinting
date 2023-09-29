package umbjm.ft.inf.mydigitalprinting.admin.itemkeranjang

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import umbjm.ft.inf.mydigitalprinting.R

class ItemKeranjangActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var keranjangItem: ArrayList<KeranjangItem>
    private lateinit var keranjangAdapter: ItemKeranjangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_keranjang)

        recyclerView = findViewById(R.id.rv_keranjang)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()
        keranjangItem = arrayListOf<KeranjangItem>()
        keranjangAdapter = ItemKeranjangAdapter(keranjangItem)
        recyclerView.adapter = keranjangAdapter

        getData()

    }


    private fun getData() {
        database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                keranjangItem.clear() // Membersihkan list terlebih dahulu

                for (userSnapshot in dataSnapshot.children) {
                    // Loop melalui semua data pengguna
                    val userID = userSnapshot.key // Ini akan berisi userID dari setiap pengguna
                    if (userID != null) {
                        // Dapatkan data pesanan untuk pengguna tertentu
                        val pesananReference = userSnapshot.child("Keranjang").ref
                        pesananReference.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(pesananSnapshot: DataSnapshot) {
                                for (keranjangSnapshot in pesananSnapshot.children) {
                                    val pesanan = keranjangSnapshot.getValue(KeranjangItem::class.java)
                                    if (pesanan != null) {
                                        keranjangItem.add(pesanan)
                                    }
                                }
                                keranjangAdapter.notifyDataSetChanged()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Penanganan kesalahan jika diperlukan
                            }
                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()
            }
        })

    }
}