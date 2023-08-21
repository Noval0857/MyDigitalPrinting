package umbjm.ft.inf.mydigitalprinting.produk

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.item.GridBrosur
import umbjm.ft.inf.mydigitalprinting.item.GridItem2

class BrosurActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var recyclerView: RecyclerView
    lateinit var listbrosur: ArrayList<GridItem2>
    lateinit var gridBrosur: GridBrosur

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brosur)

        init()

    }

    private fun init(){
        recyclerView = findViewById(R.id.rvBrosur)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        listbrosur = ArrayList()
        addDataToList()

        gridBrosur = GridBrosur(listbrosur)
        recyclerView.adapter = gridBrosur
    }

    private fun addDataToList(){
        listbrosur.add(GridItem2(R.drawable.brosur1, "Brosur A4", "Rp. 450.000,00"))
        listbrosur.add(GridItem2(R.drawable.brosur2, "Brosur A5", "Rp. 350.000,00"))
        listbrosur.add(GridItem2(R.drawable.brosur3, "Flyer", "Rp. 20.000/M"))
        listbrosur.add(GridItem2(R.drawable.brosur4, "Brosur A6", "Rp. 450.000,00"))
        listbrosur.add(GridItem2(R.drawable.brosur5, "Brosur A3", "Rp. 450.000,00"))
    }
}