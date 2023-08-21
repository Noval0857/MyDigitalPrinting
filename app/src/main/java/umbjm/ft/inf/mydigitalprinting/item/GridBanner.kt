package umbjm.ft.inf.mydigitalprinting.item

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsipesananActivity

class GridBanner(private val listbanner:ArrayList<GridItem2>) : RecyclerView.Adapter<GridBanner.GridViewHolder2>(){

    class GridViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image_grid: ImageView = itemView.findViewById(R.id.image_grid)
        val text_grid: TextView = itemView.findViewById(R.id.text_grid)
        val harga_grid: TextView = itemView.findViewById(R.id.harga_grid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)
        return GridViewHolder2(view)
    }

    override fun getItemCount(): Int {
        return listbanner.size
    }

    override fun onBindViewHolder(holder: GridViewHolder2, position: Int) {
        val grid = listbanner[position]
        holder.image_grid.setImageResource(grid.image)
        holder.text_grid.text = grid.name
        holder.harga_grid.text = grid.harga

        holder.itemView.setOnClickListener {

            when (grid.name) {
                "Rollbanner 80 x 200" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Rollbanner 60 x 160" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Spanduk" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "X-Banner" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Y-Banner" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Tripod Banner" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Roll Up Banner" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Event Desk" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Door Frame Banner" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }


            }
        }
    }
}