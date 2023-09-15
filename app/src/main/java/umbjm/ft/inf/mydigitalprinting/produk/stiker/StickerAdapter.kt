package umbjm.ft.inf.mydigitalprinting.produk.stiker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.R

class StickerAdapter(private val stickerItem: ArrayList<StickerItem>, private val itemClickListener: StickerItemClickListener) : RecyclerView.Adapter<StickerAdapter.StickerHolder>(){

    inner class StickerHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.image_grid)
        val nameView: TextView = itemView.findViewById(R.id.text_grid)
        val hargaView: TextView = itemView.findViewById(R.id.harga_grid)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val idSticker = stickerItem[position].idSticker
                    val harga = stickerItem[position].harga
                    if (idSticker != null && harga != null) {
                        itemClickListener.onItemClick(idSticker, harga)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)
        return StickerHolder(view)
    }

    override fun getItemCount(): Int {
        return stickerItem.size
    }

    override fun onBindViewHolder(holder: StickerHolder, position: Int) {
        val currentitem = stickerItem[position]
        holder.nameView.text = currentitem.nameSticker
        holder.hargaView.text = currentitem.harga
        // Periksa apakah currentitem.image tidak kosong dan tidak null
        if (!currentitem.imageSticker.isNullOrEmpty()) {
            Picasso.get().load(currentitem.imageSticker).into(holder.imageView)
        } else {
            // Handle kasus jika path gambar kosong atau null, misalnya tampilkan gambar placeholder
            Picasso.get().load(R.drawable.banner1).into(holder.imageView)
        }
    }

    interface StickerItemClickListener {
        fun onItemClick(idSticker: String, harga: String)
    }


}