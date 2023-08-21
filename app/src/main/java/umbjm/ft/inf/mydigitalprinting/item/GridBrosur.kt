package umbjm.ft.inf.mydigitalprinting.item

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsiidcardActivity
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsipesananActivity

class GridBrosur(private val listbrosur:ArrayList<GridItem2>) : RecyclerView.Adapter<GridBrosur.GridViewHolder2>(){

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
        return listbrosur.size
    }

    override fun onBindViewHolder(holder: GridViewHolder2, position: Int) {
        val grid = listbrosur[position]
        holder.image_grid.setImageResource(grid.image)
        holder.text_grid.text = grid.name
        holder.harga_grid.text = grid.harga

        holder.itemView.setOnClickListener {

            when (grid.name) {
                "Brosur A4" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Brosur A5" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Brosur A6" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Brosur A3" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Flyer" -> {
                    val intent = Intent(holder.itemView.context, OpsipesananActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }
}