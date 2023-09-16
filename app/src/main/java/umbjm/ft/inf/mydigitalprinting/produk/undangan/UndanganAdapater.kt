package umbjm.ft.inf.mydigitalprinting.produk.undangan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.R

class UndanganAdapater(private val undanganItem: ArrayList<UndanganItem>, private val itemClickListener: UndanganItemClickListener) : RecyclerView.Adapter<UndanganAdapater.UndanganHolder>(){

    inner class UndanganHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.image_grid)
        val nameView: TextView = itemView.findViewById(R.id.text_grid)
        val hargaView: TextView = itemView.findViewById(R.id.harga_grid)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val idUndangan = undanganItem[position].idUndangan
                    val harga = undanganItem[position].harga
                    if (idUndangan != null && harga != null) {
                        itemClickListener.onItemClick(idUndangan, harga)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UndanganHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)
        return UndanganHolder(view)
    }

    override fun getItemCount(): Int {
        return undanganItem.size
    }

    override fun onBindViewHolder(holder: UndanganHolder, position: Int) {
        val currentitem = undanganItem[position]
        holder.nameView.text = currentitem.nameUndangan
        holder.hargaView.text = currentitem.harga
        // Periksa apakah currentitem.image tidak kosong dan tidak null
        if (!currentitem.imageUndangan.isNullOrEmpty()) {
            Picasso.get().load(currentitem.imageUndangan).into(holder.imageView)
        } else {
            // Handle kasus jika path gambar kosong atau null, misalnya tampilkan gambar placeholder
            Picasso.get().load(R.drawable.banner1).into(holder.imageView)
        }
    }

    interface UndanganItemClickListener {
        fun onItemClick(idUndangan: String, harga: String)
    }


}