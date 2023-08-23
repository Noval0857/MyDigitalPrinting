package umbjm.ft.inf.mydigitalprinting.produk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.item.GridItem2
import umbjm.ft.inf.mydigitalprinting.item.GridKalender
import umbjm.ft.inf.mydigitalprinting.item.GridUndangan

class KalenderActivity : AppCompatActivity() {


    lateinit var toggle: ActionBarDrawerToggle
    lateinit var recyclerView: RecyclerView
    lateinit var listkalender: ArrayList<GridItem2>
    lateinit var gridKalender: GridKalender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kalender)

        init()

    }

    private fun init(){
        recyclerView = findViewById(R.id.rvKalender)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        listkalender = ArrayList()
        addDataToList()

        gridKalender = GridKalender(listkalender)
        recyclerView.adapter = gridKalender
    }

    private fun addDataToList(){
        listkalender.add(GridItem2(R.drawable.kalender1, "Kalender Poster", "Rp. 450.000,00"))
        listkalender.add(GridItem2(R.drawable.kalender2, "Kalender Meja", "Rp. 450.000,00"))
        listkalender.add(GridItem2(R.drawable.kalender3, "Kalender Dinding", "Rp. 450.000,00"))
    }
}