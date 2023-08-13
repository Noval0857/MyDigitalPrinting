package umbjm.ft.inf.mydigitalprinting.produk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.item.GridAdapter
import umbjm.ft.inf.mydigitalprinting.item.GridAdapter2
import umbjm.ft.inf.mydigitalprinting.item.GridItem
import umbjm.ft.inf.mydigitalprinting.item.GridItem2

class BannerActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var recyclerView: RecyclerView
    lateinit var gridList2: ArrayList<GridItem2>
    lateinit var gridAdapter2: GridAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

        init()

    }

    private fun init(){
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        gridList2 = ArrayList()
        addDataToList()

        gridAdapter2 = GridAdapter2(gridList2)
        recyclerView.adapter = gridAdapter2
    }

    private fun addDataToList(){
        gridList2.add(GridItem2(R.drawable.banner1, "Rollbanner 80 x 200", "Rp. 450.000,00"))
        gridList2.add(GridItem2(R.drawable.banner1, "Rollbanner 60 x 160", "Rp. 350.000,00"))
        gridList2.add(GridItem2(R.drawable.banner2, "Spanduk", "Rp. 20.000/M"))
        gridList2.add(GridItem2(R.drawable.banner3, "X-Banner", "Rp. 65.000,00"))
        gridList2.add(GridItem2(R.drawable.banner4, "Y-banner", "Rp. 20.000/M"))
        gridList2.add(GridItem2(R.drawable.banner5, "Tripod Banner", "Rp. 20.000/M"))
        gridList2.add(GridItem2(R.drawable.banner6, "Roll Up Banner", "Rp. 150.000,00"))
        gridList2.add(GridItem2(R.drawable.banner7, "Door Frame Banner", "Rp. 250.000/M"))
        gridList2.add(GridItem2(R.drawable.banner8, "Event Desk", "Rp. 250.000/M"))
    }
}