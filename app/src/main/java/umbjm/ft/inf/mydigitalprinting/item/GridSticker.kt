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
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsistickerActivity

class GridSticker(private val liststicker:ArrayList<GridItem2>) : RecyclerView.Adapter<GridSticker.GridViewHolder2>(){

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
        return liststicker.size
    }

    override fun onBindViewHolder(holder: GridViewHolder2, position: Int) {
        val grid = liststicker[position]
        holder.image_grid.setImageResource(grid.image)
        holder.text_grid.text = grid.name
        holder.harga_grid.text = grid.harga

        holder.itemView.setOnClickListener {

            when (grid.name) {
                "Vinyl Transparan" -> {
                    val intent = Intent(holder.itemView.context, OpsistickerActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Vinyl Putih" -> {
                    val intent = Intent(holder.itemView.context, OpsistickerActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }
}