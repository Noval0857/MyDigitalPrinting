package umbjm.ft.inf.mydigitalprinting.keranjang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityKeranjangBinding

class KeranjangActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var keranjangItem: ArrayList<KeranjangItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        recyclerView = findViewById(R.id.rv_keranjang)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()
        keranjangItem = arrayListOf<KeranjangItem>()
        getData()
    }

    private fun getData() {
        database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Produk")
        database.limitToFirst(2).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    keranjangItem.clear()  // Membersihkan list terlebih dahulu
                    for (itemProduk in snapshot.children) {
                        val item = itemProduk.getValue(KeranjangItem::class.java)
                        keranjangItem.add(item!!)
                    }
                    recyclerView.adapter = KeranjangAdapter(keranjangItem)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Error handling
            }
        })
    }


}

//    private lateinit var binding: ActivityKeranjangBinding
//    private lateinit var storageRef: StorageReference
//    private lateinit var database: DatabaseReference
//    private lateinit var keranjangItem: ArrayList<KeranjangItem>
//    private lateinit var adapter: KeranjangAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityKeranjangBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        initLayout()
//        getRecyclerData()
//    }
//
//    private fun initLayout(){
//        // Inisialisasi database Firebase
//        database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").reference.child("Produk")
//
//        // Set up RecyclerView
//        binding.rvKeranjang.setHasFixedSize(true)
//        binding.rvKeranjang.layoutManager = LinearLayoutManager(this)
//        keranjangItem = arrayListOf()
//        adapter = KeranjangAdapter(keranjangItem)
//        binding.rvKeranjang.adapter = adapter
//    }
//
//    private fun getRecyclerData(){
//        database.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (productSnapshot in dataSnapshot.children) {
//                    val keranjangItems = productSnapshot.child("produk").getValue(String::class.java)
//                    if (keranjangItems != null) {
//                        keranjangItem.addAll(keranjangItem)
//                    }
//                }
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle error
//            }
//        })
//    }
//}

//    private fun readData(Jenis){
//        database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Spesifikasi Desain")
//        database.child(Jenis).get().addOnSuccessListener {
//            if (it.exists()){
//                val Jenis = it.child("Jenis").value
//                val TeksUtama = it.child("TeksUtama").value
//                Toast.makeText(this, "Data", Toast.LENGTH_SHORT).show()
//                binding.
//            }
//        }
//    }
//}
//class KeranjangActivity : AppCompatActivity() {
//
//
//    private lateinit var recyclerView: RecyclerView
//    lateinit var keranjangItem: ArrayList<KeranjangItem>
//    lateinit var keranjangAdapter: KeranjangAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_keranjang2)
//
//        initLayout()
//    }
//
//    private fun initLayout() {
//        recyclerView = findViewById(R.id.rv_keranjang)
//        recyclerView.setHasFixedSize(true)
//
//        keranjangItem = ArrayList()
//
//        keranjangAdapter = KeranjangAdapter(keranjangItem)
//        recyclerView.adapter = keranjangAdapter
//        ApiRetrofit().endpoint.getProduk()
//            .enqueue(object : Callback<Keranjang>{
//                @SuppressLint("NotifyDataSetChanged")
//                override fun onResponse(call: Call<Keranjang>, response: Response<Keranjang>) {
//                    if (response.isSuccessful){
//                        val keranjang = response.body()
//                        if (keranjang != null){
//                            keranjangItem.addAll(keranjang.data)
//                            keranjangAdapter.notifyDataSetChanged()
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<Keranjang>, t: Throwable) {
//                    Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
//                }
//
//
//            })
//
//    }
//
//}

