package umbjm.ft.inf.mydigitalprinting.keranjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import umbjm.ft.inf.mydigitalprinting.R

class KeranjangAdapter(private val keranjangItem: ArrayList<KeranjangItem>) : RecyclerView.Adapter<KeranjangAdapter.KeranjangHolder>(){

    class KeranjangHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.image)
        val jenis: TextView = itemView.findViewById(R.id.jenis)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.keranjang_item, parent, false)
        return KeranjangHolder(view)
    }

    override fun getItemCount(): Int {
        return keranjangItem.size
    }

    override fun onBindViewHolder(holder: KeranjangHolder, position: Int) {
        holder.jenis.text = keranjangItem[position].jenis
        Glide.with(holder.image)
            .load(keranjangItem[position].image)
            .transition(DrawableTransitionOptions.withCrossFade()) // Opsi animasi jika diperlukan
            .into(holder.image)
    }
}