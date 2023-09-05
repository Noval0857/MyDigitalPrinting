package umbjm.ft.inf.mydigitalprinting

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import umbjm.ft.inf.mydigitalprinting.item.GridAdapter
import umbjm.ft.inf.mydigitalprinting.item.GridItem
import umbjm.ft.inf.mydigitalprinting.keranjang.KeranjangActivity
import umbjm.ft.inf.mydigitalprinting.profil.ProfileActivity

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var recyclerView: RecyclerView
    lateinit var gridList: ArrayList<GridItem>
    lateinit var gridAdapter: GridAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ActionBar agar dapat di konfigurasi di halaman Activity_Main
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Mengambil fungsi init
        init()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)

        toggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    // Mengambil data yang ada pada loginactivity
    override fun onResume() {
        super.onResume()

        // menampung data yang ada pada login_status didalam variable loginstatus
        val loginstatus = intent.getStringExtra("login_status")
        // jika sukses mengambil data maka akan menampilkan sebuah alert
        if (loginstatus == "success"){
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

    // Fungsi toolbar jika diclick
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.keranjang -> {
            // Konfigurasikan jika ingin pindah halaman
                val intent = Intent(this, KeranjangActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.Tolprofile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.Tolsetting -> {
            // Konfigurasikan jika ingin pindah halaman

            }

            R.id.Tolpesanan -> {
            // Konfigurasikan jika ingin pindah halaman
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        recyclerView = findViewById(R.id.recylerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        gridList = ArrayList()
        addDataToList()

        gridAdapter = GridAdapter(gridList)
        recyclerView.adapter = gridAdapter
    }

    private fun addDataToList() {
        gridList.add(GridItem(R.drawable.banner, "Banner"))
        gridList.add(GridItem(R.drawable.id_card, "Id Card"))
        gridList.add(GridItem(R.drawable.brosur, "Brosur"))
        gridList.add(GridItem(R.drawable.sticker, "Sticker"))
        gridList.add(GridItem(R.drawable.banner, "Undangan"))
        gridList.add(GridItem(R.drawable.id_card, "Sertifikat"))
        gridList.add(GridItem(R.drawable.brosur, "Kalender"))
        gridList.add(GridItem(R.drawable.sticker, "Poster"))
        gridList.add(GridItem(R.drawable.sticker, "Spanduk"))
    }
}