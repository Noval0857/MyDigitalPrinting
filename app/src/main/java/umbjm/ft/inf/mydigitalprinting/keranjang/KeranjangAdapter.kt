package umbjm.ft.inf.mydigitalprinting.keranjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.R

class KeranjangAdapter(private val keranjangItems: ArrayList<KeranjangItem>) : RecyclerView.Adapter<KeranjangAdapter.KeranjangHolder>(){

    inner class KeranjangHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val outImage: ImageView = itemView.findViewById(R.id.imageView)
        val outJenis: TextView = itemView.findViewById(R.id.jenisView)
        val outHarga: TextView = itemView.findViewById(R.id.hargaView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.keranjang_item, parent, false)
        return KeranjangHolder(view)
    }

    override fun getItemCount(): Int {
        return keranjangItems.size
    }

    override fun onBindViewHolder(holder: KeranjangHolder, position: Int) {
        val currentitem = keranjangItems[position]
        holder.outJenis.text = currentitem.jenis
        holder.outHarga.text = currentitem.hargaBanner
        Picasso.get().load(currentitem.image).into(holder.outImage)
    }

}

