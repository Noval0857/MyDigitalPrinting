package umbjm.ft.inf.mydigitalprinting.produk.sertifikat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.sertifikat.SertifikatItem

class SertifikatAdapter(private val sertifikatItem: ArrayList<SertifikatItem>, private val itemClickListener: SertifikatItemClickListener) : RecyclerView.Adapter<SertifikatAdapter.SertifikatHolder>(){

    inner class SertifikatHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.image_grid)
        val nameView: TextView = itemView.findViewById(R.id.text_grid)
        val hargaView: TextView = itemView.findViewById(R.id.harga_grid)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val idSertifikat = sertifikatItem[position].idSertifikat
                    val harga = sertifikatItem[position].harga
                    if (idSertifikat != null && harga != null) {
                        itemClickListener.onItemClick(idSertifikat, harga)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SertifikatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)
        return SertifikatHolder(view)
    }

    override fun getItemCount(): Int {
        return sertifikatItem.size
    }

    override fun onBindViewHolder(holder: SertifikatHolder, position: Int) {
        val currentitem = sertifikatItem[position]
        holder.nameView.text = currentitem.nameSertifikat
        holder.hargaView.text = currentitem.harga
        // Periksa apakah currentitem.image tidak kosong dan tidak null
        if (!currentitem.imageSertifikat.isNullOrEmpty()) {
            Picasso.get().load(currentitem.imageSertifikat).into(holder.imageView)
        } else {
            // Handle kasus jika path gambar kosong atau null, misalnya tampilkan gambar placeholder
            Picasso.get().load(R.drawable.banner1).into(holder.imageView)
        }
    }

    interface SertifikatItemClickListener {
        fun onItemClick(idSertifikat: String, harga: String)
    }


}