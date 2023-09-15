package umbjm.ft.inf.mydigitalprinting.produk.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.R

class BannerAdapter(private val bannerItem: ArrayList<BannerItem>, private val itemClickListener: BannerItemClickListener) : RecyclerView.Adapter<BannerAdapter.BannerHolder>(){

    inner class BannerHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.image_grid)
        val nameView: TextView = itemView.findViewById(R.id.text_grid)
        val hargaView: TextView = itemView.findViewById(R.id.harga_grid)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val idBanner = bannerItem[position].idBanner
                    val harga = bannerItem[position].harga
                    if (idBanner != null && harga != null) {
                        itemClickListener.onItemClick(idBanner, harga)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)
        return BannerHolder(view)
    }

    override fun getItemCount(): Int {
        return bannerItem.size
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        val currentitem = bannerItem[position]
        holder.nameView.text = currentitem.nameBanner
        holder.hargaView.text = currentitem.harga
        // Periksa apakah currentitem.image tidak kosong dan tidak null
        if (!currentitem.imageBanner.isNullOrEmpty()) {
            Picasso.get().load(currentitem.imageBanner).into(holder.imageView)
        } else {
            // Handle kasus jika path gambar kosong atau null, misalnya tampilkan gambar placeholder
            Picasso.get().load(R.drawable.banner1).into(holder.imageView)
        }
    }

    interface BannerItemClickListener {
        fun onItemClick(idBanner: String, harga: String)
    }


}