package umbjm.ft.inf.mydigitalprinting.item

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.BannerActivity
import umbjm.ft.inf.mydigitalprinting.produk.BrosurActivity
import umbjm.ft.inf.mydigitalprinting.produk.IdCardActivity
import umbjm.ft.inf.mydigitalprinting.produk.KalenderActivity
import umbjm.ft.inf.mydigitalprinting.produk.PosterActivity
import umbjm.ft.inf.mydigitalprinting.produk.SertifikatActivity
import umbjm.ft.inf.mydigitalprinting.produk.StickerActivity
import umbjm.ft.inf.mydigitalprinting.produk.UndanganActivity

class GridAdapter2(private val gridlist2:ArrayList<GridItem2>) : RecyclerView.Adapter<GridAdapter2.GridViewHolder2>(){

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
        return gridlist2.size
    }

    override fun onBindViewHolder(holder: GridViewHolder2, position: Int) {
        val grid = gridlist2[position]
        holder.image_grid.setImageResource(grid.image)
        holder.text_grid.text = grid.name
        holder.harga_grid.text = grid.harga

        holder.itemView.setOnClickListener {

            when (grid.name) {
                "Banner" -> {
                    val intent = Intent(holder.itemView.context, BannerActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Sticker" -> {
                    val intent = Intent(holder.itemView.context, StickerActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Brosur" -> {
                    val intent = Intent(holder.itemView.context, BrosurActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Id Card" -> {
                    val intent = Intent(holder.itemView.context, IdCardActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Sertifikat" -> {
                    val intent = Intent(holder.itemView.context, SertifikatActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Kalender" -> {
                    val intent = Intent(holder.itemView.context, KalenderActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Poster" -> {
                    val intent = Intent(holder.itemView.context, PosterActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
                "Undangan" -> {
                    val intent = Intent(holder.itemView.context, UndanganActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }

            }
        }
    }
}