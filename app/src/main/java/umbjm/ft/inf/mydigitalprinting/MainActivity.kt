package umbjm.ft.inf.mydigitalprinting

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import umbjm.ft.inf.mydigitalprinting.item.GridAdapter
import umbjm.ft.inf.mydigitalprinting.item.GridItem
import umbjm.ft.inf.mydigitalprinting.keranjang.KeranjangActivity
import umbjm.ft.inf.mydigitalprinting.pesanan.PesananActivity
import umbjm.ft.inf.mydigitalprinting.pesanan.PilihanPesanan
import umbjm.ft.inf.mydigitalprinting.produk.banner.BannerActivity
import umbjm.ft.inf.mydigitalprinting.produk.brosur.BrosurActivity
import umbjm.ft.inf.mydigitalprinting.produk.idcard.IdCardActivity
import umbjm.ft.inf.mydigitalprinting.produk.sertifikat.SertifikatActivity
import umbjm.ft.inf.mydigitalprinting.produk.stiker.StickerActivity
import umbjm.ft.inf.mydigitalprinting.produk.undangan.UndanganActivity
import umbjm.ft.inf.mydigitalprinting.profil.ProfileActivity
import java.sql.Connection

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var recyclerView: RecyclerView
    lateinit var gridList: ArrayList<GridItem>
    private lateinit var database: DatabaseReference
    private lateinit var BottomNavigationView: BottomNavigationView
    private var isBottomNavVisible = true
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Untuk Mode NightMode dan LightMode
        val sharedPreferences = getSharedPreferences("Mode", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val nightMode = sharedPreferences.getBoolean("night", false)
        val switch = findViewById<SwitchCompat>(R.id.ModeTheme)

        if (nightMode) {
            switch.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("Night Mode Turn Off", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("Night Mode Turn On", true)
                editor.apply()
            }
        }

        // ActionBar agar dapat di konfigurasi di halaman Activity_Main
        fun refershActivity() {
            val intent = intent
            finish()
            startActivity(intent)
        }

        BottomNavigationView = findViewById(R.id.bNavigation)

        // Animation untuk Bottom Navigation bar
        val slideUp =
            AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_slide_in_bottom)
        val slideDown =
            AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_slide_out_bottom)

        recyclerView = findViewById(R.id.recylerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        gridList = arrayListOf<GridItem>()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && isBottomNavVisible) {
                    BottomNavigationView.startAnimation(slideDown)
                    BottomNavigationView.visibility = View.GONE
                    isBottomNavVisible = false
                } else if (dy < 0 && !isBottomNavVisible) {
                    BottomNavigationView.startAnimation(slideUp)
                    BottomNavigationView.visibility = View.VISIBLE
                    isBottomNavVisible = true
                }
            }
        })

        // Untuk fungsi button pada Bottom Navigation Bar
        BottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Bkeranjang -> {
                    val intent = Intent(this, KeranjangActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.Bprofile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                // jika kembali menekan home maka kan refresh halaman
                R.id.Bhome -> {
                    refershActivity()
                    true
                }

                else -> false
            }
        }

        // Mengambil fungsi init
        init()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)

        toggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Check Internet
        if(!isNetworkAvailable()){
            showNoInternetAlert()
        }

    }

    // Untuk menampilkan pesan jika tidak ada internet
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork != null){
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        }
        return false
    }

    private fun showNoInternetAlert(){
        SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")
            .setContentText("Periksa internet anda")
            .show()
    }

    // Mengambil data yang ada pada loginactivity
    override fun onResume() {
        super.onResume()

        // menampung data yang ada pada login_status didalam variable loginstatus
        val loginstatus = intent.getStringExtra("login_status")
        // jika sukses mengambil data maka akan menampilkan sebuah alert
        if (loginstatus == "success") {
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText("Berhasil Login")
                .show()
            // menghapus data berikutnya jika dipanggil lagi
            intent.removeExtra("login_status")
        }
    }

    // Untuk menampilkan Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //     Fungsi toolbar jika diclick
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.Bkeranjang -> {
                // Konfigurasikan jika ingin pindah halaman
                val intent = Intent(this, KeranjangActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.Bprofile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        recyclerView = findViewById(R.id.recylerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        gridList = arrayListOf<GridItem>()

        getData()
        recyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val selectedProduct = gridList[position]
                when (selectedProduct.name) {
                    "Banner" -> {
                        val intent = Intent(this@MainActivity, BannerActivity::class.java)
                        intent.putExtra("id", selectedProduct.id)
                        startActivity(intent)
                    }

                    "Id Card" -> {
                        val intent = Intent(this@MainActivity, IdCardActivity::class.java)
                        intent.putExtra("id", selectedProduct.id)
                        startActivity(intent)
                    }

                    "Brosur" -> {
                        val intent = Intent(this@MainActivity, BrosurActivity::class.java)
                        intent.putExtra("id", selectedProduct.id)
                        startActivity(intent)
                    }

                    "Sticker" -> {
                        val intent = Intent(this@MainActivity, StickerActivity::class.java)
                        intent.putExtra("id", selectedProduct.id)
                        startActivity(intent)
                    }

                    "Undangan" -> {
                        val intent = Intent(this@MainActivity, UndanganActivity::class.java)
                        intent.putExtra("id", selectedProduct.id)
                        startActivity(intent)
                    }

                    "Sertifikat" -> {
                        val intent = Intent(this@MainActivity, SertifikatActivity::class.java)
                        intent.putExtra("id", selectedProduct.id)
                        startActivity(intent)
                    }
                }
            }
        })
    }

    // Buat sebuah interface OnItemClickListener
    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    // Tambahkan ekstensi untuk RecyclerView untuk menambahkan onItemClickListener
    fun RecyclerView.addOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                view.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    onItemClickListener.onItemClicked(holder.adapterPosition, view)
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                // Tidak ada tindakan yang perlu diambil saat view dilepas dari window
            }
        })
    }


    private fun getData() {
        database =
            FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Products")
        database.child("produk").orderByChild("id")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        gridList.clear()  // Membersihkan list terlebih dahulu
                        for (itemProduk in snapshot.children) {
                            val item = itemProduk.getValue(GridItem::class.java)
                            gridList.add(item!!)
                        }
                        recyclerView.adapter = GridAdapter(gridList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Error handling
                }
            })
    }
}