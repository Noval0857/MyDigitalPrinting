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
import umbjm.ft.inf.mydigitalprinting.produk.SertifikatActivity
import umbjm.ft.inf.mydigitalprinting.produk.StickerActivity

class GridAdapter(private val gridList:ArrayList<GridItem>) : RecyclerView.Adapter<GridAdapter.GridViewHolder>(){

    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image_grid: ImageView = itemView.findViewById(R.id.image_grid)
        val text_grid: TextView = itemView.findViewById(R.id.text_grid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return GridViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gridList.size
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val grid = gridList[position]
        holder.image_grid.setImageResource(grid.image)
        holder.text_grid.text = grid.name

        holder.itemView.setOnClickListener {
            // Tangani klik item dan navigasi ke halaman yang sesuai
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
                // Tambahkan kasus lain untuk item lain jika diperlukan
            }
        }
    }
}