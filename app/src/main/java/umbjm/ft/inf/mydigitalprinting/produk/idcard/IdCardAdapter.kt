package umbjm.ft.inf.mydigitalprinting.produk.idcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.produk.brosur.BrosurAdapter
import umbjm.ft.inf.mydigitalprinting.produk.brosur.BrosurItem

class IdCardAdapter(private val idcardItem: ArrayList<IdCardItem>, private val itemClickListener: IdCardItemClickListener) : RecyclerView.Adapter<IdCardAdapter.IdCardHolder>() {

    inner class IdCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_grid)
        val nameView: TextView = itemView.findViewById(R.id.text_grid)
        val hargaView: TextView = itemView.findViewById(R.id.harga_grid)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val idCard = idcardItem[position].idCard
                    val harga = idcardItem[position].harga
                    if (idCard != null && harga != null) {
                        itemClickListener.onItemClick(idCard, harga)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdCardHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)
        return IdCardHolder(view)
    }

    override fun getItemCount(): Int {
        return idcardItem.size
    }

    override fun onBindViewHolder(holder: IdCardHolder, position: Int) {
        val currentitem = idcardItem[position]
        holder.nameView.text = currentitem.nameIdcard
        holder.hargaView.text = currentitem.harga
        // Periksa apakah currentitem.image tidak kosong dan tidak null
        if (!currentitem.imageIdcard.isNullOrEmpty()) {
            Picasso.get().load(currentitem.imageIdcard).into(holder.imageView)
        } else {
            // Handle kasus jika path gambar kosong atau null, misalnya tampilkan gambar placeholder
            Picasso.get().load(R.drawable.banner1).into(holder.imageView)
        }
    }

    interface IdCardItemClickListener {
        fun onItemClick(idCard: String, harga : String)
    }

}