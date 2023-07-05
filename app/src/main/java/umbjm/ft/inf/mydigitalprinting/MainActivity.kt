package umbjm.ft.inf.mydigitalprinting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import umbjm.ft.inf.mydigitalprinting.item.GridAdapter
import umbjm.ft.inf.mydigitalprinting.item.GridItem

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var recyclerView: RecyclerView
    lateinit var gridList: ArrayList<GridItem>
    lateinit var gridAdapter: GridAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        val drawerLayout:DrawerLayout = findViewById(R.id.drawerLayout)
        val navigationView:NavigationView = findViewById(R.id.navigationView)

        toggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    private fun init(){
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        gridList = ArrayList()
        addDataToList()

        gridAdapter = GridAdapter(gridList)
        recyclerView.adapter = gridAdapter
    }

    private fun addDataToList(){
        gridList.add(GridItem(R.drawable.banner, "Banner"))
        gridList.add(GridItem(R.drawable.id_card, "Id Card"))
        gridList.add(GridItem(R.drawable.brosur, "Brosur"))
        gridList.add(GridItem(R.drawable.sticker, "Sticker"))
    }
}