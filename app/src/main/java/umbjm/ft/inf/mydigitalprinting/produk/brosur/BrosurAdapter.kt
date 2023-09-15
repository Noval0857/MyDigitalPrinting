package umbjm.ft.inf.mydigitalprinting.produk.brosur

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.R

class BrosurAdapter(private val brosurItem: ArrayList<BrosurItem>, private val itemClickListener: BrosurItemClickListener) : RecyclerView.Adapter<BrosurAdapter.BrosurHolder>(){

    inner class BrosurHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.image_grid)
        val nameView: TextView = itemView.findViewById(R.id.text_grid)
        val hargaView: TextView = itemView.findViewById(R.id.harga_grid)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val idBrosur = brosurItem[position].idBrosur
                    val harga = brosurItem[position].harga
                    if (idBrosur != null && harga != null) {
                        itemClickListener.onItemClick(idBrosur, harga)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrosurHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)
        return BrosurHolder(view)
    }

    override fun getItemCount(): Int {
        return brosurItem.size
    }

    override fun onBindViewHolder(holder: BrosurHolder, position: Int) {
        val currentitem = brosurItem[position]
        holder.nameView.text = currentitem.nameBrosur
        holder.hargaView.text = currentitem.harga
        // Periksa apakah currentitem.image tidak kosong dan tidak null
        if (!currentitem.imageBrosur.isNullOrEmpty()) {
            Picasso.get().load(currentitem.imageBrosur).into(holder.imageView)
        } else {
            // Handle kasus jika path gambar kosong atau null, misalnya tampilkan gambar placeholder
            Picasso.get().load(R.drawable.banner1).into(holder.imageView)
        }
    }

    interface BrosurItemClickListener {
        fun onItemClick(idBrosur: String, harga: String)
    }


}