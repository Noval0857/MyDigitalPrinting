package umbjm.ft.inf.mydigitalprinting.produk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.item.GridBanner
import umbjm.ft.inf.mydigitalprinting.item.GridItem2
import umbjm.ft.inf.mydigitalprinting.item.GridSticker

class StickerActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var recyclerView: RecyclerView
    lateinit var liststicker: ArrayList<GridItem2>
    lateinit var gridSticker: GridSticker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticker)

        init()

    }

    private fun init(){
        recyclerView = findViewById(R.id.rvSticker)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        liststicker = ArrayList()
        addDataToList()

        gridSticker = GridSticker(liststicker)
        recyclerView.adapter = gridSticker
    }

    private fun addDataToList(){
        liststicker.add(GridItem2(R.drawable.sticker1, "Vinyl Transparan", "Rp. 450.000,00"))
        liststicker.add(GridItem2(R.drawable.sticker2, "Vinyl Putih", "Rp. 450.000,00"))
    }
}