package umbjm.ft.inf.mydigitalprinting.produk

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.item.GridBanner
import umbjm.ft.inf.mydigitalprinting.item.GridItem2

class BannerActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var recyclerView: RecyclerView
    lateinit var listbanner: ArrayList<GridItem2>
    lateinit var gridBanner: GridBanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

        init()

    }

    private fun init(){
        recyclerView = findViewById(R.id.rvBanner)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        listbanner = ArrayList()
        addDataToList()

        gridBanner = GridBanner(listbanner)
        recyclerView.adapter = gridBanner
    }

    private fun addDataToList(){
        listbanner.add(GridItem2(R.drawable.banner1, "Rollbanner 80 x 200", "Rp. 450.000,00"))
        listbanner.add(GridItem2(R.drawable.banner1, "Rollbanner 60 x 160", "Rp. 350.000,00"))
        listbanner.add(GridItem2(R.drawable.banner2, "Spanduk", "Rp. 20.000/M"))
        listbanner.add(GridItem2(R.drawable.banner3, "X-Banner", "Rp. 65.000,00"))
        listbanner.add(GridItem2(R.drawable.banner4, "Y-banner", "Rp. 20.000/M"))
        listbanner.add(GridItem2(R.drawable.banner5, "Tripod Banner", "Rp. 20.000/M"))
        listbanner.add(GridItem2(R.drawable.banner6, "Roll Up Banner", "Rp. 150.000,00"))
        listbanner.add(GridItem2(R.drawable.banner7, "Door Frame Banner", "Rp. 250.000/M"))
        listbanner.add(GridItem2(R.drawable.banner8, "Event Desk", "Rp. 250.000/M"))
    }
}