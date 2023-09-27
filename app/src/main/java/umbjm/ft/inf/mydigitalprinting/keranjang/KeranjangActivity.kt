package umbjm.ft.inf.mydigitalprinting.keranjang

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.pembayaran.PembayaranActivity
import java.text.DecimalFormat

class KeranjangActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var keranjangItem: ArrayList<KeranjangItem>
    private lateinit var HomeBack:ImageButton
    private lateinit var BtnCheckout: TextView
    private var totalHarga: Double = 0.0
    private lateinit var tv_total: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        tv_total = findViewById(R.id.tv_total)
        BtnCheckout = findViewById(R.id.BtnCheckout)

        recyclerView = findViewById(R.id.rv_keranjang)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()
        keranjangItem = arrayListOf<KeranjangItem>()
        // back mainActivity
        HomeBack = findViewById(R.id.HomeBack)
        HomeBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }


        // Periksa apakah pengguna sudah login sebelum mencoba mengambil data
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userID = user.uid // Mendapatkan ID pengguna saat ini
            getData(userID)
            bayar()



        } else {

        }
    }

    private fun bayar() {
        BtnCheckout.setOnClickListener {

            val intent = Intent(this, PembayaranActivity::class.java)
            val itemKeranjang: ArrayList<KeranjangItem> = ArrayList()

            intent.putExtra("totalHarga", totalHarga)
            startActivity(intent)
        }

    }

    private fun getData(userID: String) {
        database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")


        // Panggil getDataFirebase() dengan userID sebagai filter
        getDataFirebase(userID)
    }

    private fun getDataFirebase(userID: String) {
        database.child(userID).child("Keranjang").orderByChild("idKeranjang")
            .addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    keranjangItem.clear() // Membersihkan list terlebih dahulu
                    totalHarga = 0.0
                    for (itemProduk in snapshot.children) {
                        val item = itemProduk.getValue(KeranjangItem::class.java)
                        item?.let {
                            keranjangItem.add(it)

                            val hargaItem = it.harga?.replace(".","")?.toDoubleOrNull() ?: 0.0
                            totalHarga += hargaItem
                        }

                    }
                    recyclerView.adapter = KeranjangAdapter(keranjangItem)
                    updateTotalHarga()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle kesalahan Firebase di sini
                Toast.makeText(applicationContext, "Gagal mengambil data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateTotalHarga(){
        val formattedTotalHarga = DecimalFormat("Rp #,###.##").format(totalHarga)
        tv_total.text = formattedTotalHarga
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

