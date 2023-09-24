package umbjm.ft.inf.mydigitalprinting.item

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.R

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
        Picasso.get().load(grid.image).into(holder.image_grid)
        holder.text_grid.text = grid.name
    }
}

//        holder.itemView.setOnClickListener {
//
//            when (grid.name) {
//                "Banner" -> {
//                    val intent = Intent(holder.itemView.context, BannerActivity::class.java)
//                    holder.itemView.context.startActivity(intent)
//                }
//                "Sticker" -> {
//                    val intent = Intent(holder.itemView.context, StickerActivity::class.java)
//                    holder.itemView.context.startActivity(intent)
//                }
//                "Brosur" -> {
//                    val intent = Intent(holder.itemView.context, BrosurActivity::class.java)
//                    holder.itemView.context.startActivity(intent)
//                }
//                "Id Card" -> {
//                    val intent = Intent(holder.itemView.context, IdCardActivity::class.java)
//                    holder.itemView.context.startActivity(intent)
//                }
//                "Undangan" -> {
//                    val intent = Intent(holder.itemView.context, UndanganActivity::class.java)
//                    holder.itemView.context.startActivity(intent)
//                }
//                "Sertifikat" -> {
//                    val intent = Intent(holder.itemView.context, OpsisertifikatActivity::class.java)
//                    holder.itemView.context.startActivity(intent)
//                }
//                "Kalender" -> {
//                    val intent = Intent(holder.itemView.context, KalenderActivity::class.java)
//                    holder.itemView.context.startActivity(intent)
//                }
//                "Poster" -> {
//                    val intent = Intent(holder.itemView.context, PosterActivity::class.java)
//                    holder.itemView.context.startActivity(intent)
//                }
//            }
//        }