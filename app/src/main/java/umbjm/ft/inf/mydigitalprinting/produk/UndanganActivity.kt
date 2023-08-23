package umbjm.ft.inf.mydigitalprinting.produk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.item.GridItem2
import umbjm.ft.inf.mydigitalprinting.item.GridSticker
import umbjm.ft.inf.mydigitalprinting.item.GridUndangan

class UndanganActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var recyclerView: RecyclerView
    lateinit var listundangan: ArrayList<GridItem2>
    lateinit var gridUndangan: GridUndangan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_undangan)

        init()

    }

    private fun init(){
        recyclerView = findViewById(R.id.rvUndangan)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        listundangan = ArrayList()
        addDataToList()

        gridUndangan = GridUndangan(listundangan)
        recyclerView.adapter = gridUndangan
    }

    private fun addDataToList(){
        listundangan.add(GridItem2(R.drawable.undangan1, "Single Board (A5)", "Rp. 450.000,00"))
        listundangan.add(GridItem2(R.drawable.undangan2, "Double Board (A5)", "Rp. 450.000,00"))
    }
}