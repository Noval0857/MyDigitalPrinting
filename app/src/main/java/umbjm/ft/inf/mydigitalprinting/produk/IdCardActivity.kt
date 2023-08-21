package umbjm.ft.inf.mydigitalprinting.produk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.item.GridBanner
import umbjm.ft.inf.mydigitalprinting.item.GridIdcard
import umbjm.ft.inf.mydigitalprinting.item.GridItem2

class IdCardActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var recyclerView: RecyclerView
    lateinit var listidcard: ArrayList<GridItem2>
    lateinit var gridIdcard: GridIdcard
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_card)

        init()
    }
    private fun init(){
        recyclerView = findViewById(R.id.rvIdcard)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        listidcard = ArrayList()
        addDataToList()

        gridIdcard = GridIdcard(listidcard)
        recyclerView.adapter = gridIdcard
    }

    private fun addDataToList(){
        listidcard.add(GridItem2(R.drawable.idcard1, "Kartu Nama", "Rp. 1.000/pcs"))
        listidcard.add(GridItem2(R.drawable.idcard2, "Kartu Nama Lipat", "Rp. 350.000,00"))
        listidcard.add(GridItem2(R.drawable.idcard3, "Id Card", "Rp. 20.000/M"))
        listidcard.add(GridItem2(R.drawable.idcard4, "Thanks Card", "Rp. 450.000,00"))
        listidcard.add(GridItem2(R.drawable.idcard5, "Birthday Card", "Rp. 20.000/M"))
        listidcard.add(GridItem2(R.drawable.idcard6, "Voucher", "Rp. 20.000/M"))
    }
}