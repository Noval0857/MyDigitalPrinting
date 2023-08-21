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

class GridIdcard(private val listbanner:ArrayList<GridItem2>) : RecyclerView.Adapter<GridIdcard.GridViewHolder2>(){

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
                "Kartu Nama" -> {
                    val intent = Intent(holder.itemView.context, OpsiidcardActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Kartu Nama Lipat" -> {
                    val intent = Intent(holder.itemView.context, OpsiidcardActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Id Card" -> {
                    val intent = Intent(holder.itemView.context, OpsiidcardActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Thanks Card" -> {
                    val intent = Intent(holder.itemView.context, OpsiidcardActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Birthday Card" -> {
                    val intent = Intent(holder.itemView.context, OpsiidcardActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Voucher" -> {
                    val intent = Intent(holder.itemView.context, OpsiidcardActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }
}