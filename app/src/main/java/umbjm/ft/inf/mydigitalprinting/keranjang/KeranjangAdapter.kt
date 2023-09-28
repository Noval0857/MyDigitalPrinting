package umbjm.ft.inf.mydigitalprinting.keranjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import umbjm.ft.inf.mydigitalprinting.R

class KeranjangAdapter(private val keranjangItems: ArrayList<KeranjangItem>) : RecyclerView.Adapter<KeranjangAdapter.KeranjangHolder>(){

    inner class KeranjangHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val outImages: RecyclerView = itemView.findViewById(R.id.imagesRecyclerView)
        val outJenis: TextView = itemView.findViewById(R.id.jenisView)
        val outHarga: TextView = itemView.findViewById(R.id.hargaView)
        val batal: TextView = itemView.findViewById(R.id.BtnBatal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.keranjang_item, parent, false)
        return KeranjangHolder(view)
    }

    override fun getItemCount(): Int {
        return keranjangItems.size
    }

    override fun onBindViewHolder(holder: KeranjangHolder, position: Int) {
        val currentItem = keranjangItems[position]
        holder.outJenis.text = currentItem.namaProject
        holder.outHarga.text = currentItem.harga

        // Set up RecyclerView for images
        val imagesAdapter = ImagesAdapter(currentItem.image ?: emptyList())
        holder.outImages.adapter = imagesAdapter
        holder.outImages.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)

        holder.batal.setOnClickListener {
            val idKeranjang = currentItem.idKeranjang
            deleteItemFromDatabase(idKeranjang!!)
        }
    }

    private fun deleteItemFromDatabase(idKeranjang: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val userID = user?.uid

        val database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("User").child(userID!!).child("Keranjang")
        val itemReference = database.child(idKeranjang)

        itemReference.removeValue()
            .addOnSuccessListener {
                // Item berhasil dihapus dari database
                // Hapus item dari daftar lokal juga
                val removedItem = keranjangItems.firstOrNull { it.idKeranjang == idKeranjang }
                removedItem?.let {
                    val position = keranjangItems.indexOf(it)
                    keranjangItems.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
            }
            .addOnFailureListener { e ->
                // Error handling jika gagal menghapus item
            }
    }

}

