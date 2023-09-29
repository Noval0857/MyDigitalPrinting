package umbjm.ft.inf.mydigitalprinting.admin.itemstiker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.admin.itemidcard.ItemIdcardAdapter

class ItemStickerAdapter(private val stickerItem: ArrayList<StickerItem>) : RecyclerView.Adapter<ItemStickerAdapter.StickerHolder>() {

    inner class StickerHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val nameView: TextView = itemView.findViewById(R.id.namaView)
        val hargaView: TextView = itemView.findViewById(R.id.hargaView)
        val icEdit: Button = itemView.findViewById(R.id.editButton)
        val icDelete: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemStickerAdapter.StickerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return StickerHolder(view)
    }


    override fun getItemCount(): Int {
        return stickerItem.size
    }

    override fun onBindViewHolder(holder: ItemStickerAdapter.StickerHolder, position: Int) {
        val currentitem = stickerItem[position]
        holder.nameView.text = currentitem.nameSticker
        holder.hargaView.text = currentitem.harga
        // Periksa apakah currentitem.image tidak kosong dan tidak null
        if (!currentitem.imageSticker.isNullOrEmpty()) {
            Picasso.get().load(currentitem.imageSticker).into(holder.imageView)
        } else {
            // Handle kasus jika path gambar kosong atau null, misalnya tampilkan gambar placeholder
            Picasso.get().load(R.drawable.ic_launcher_background).into(holder.imageView)
        }

        // Menambahkan OnClickListener ke tombol edit
        holder.icEdit.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ItemStickerUpdate::class.java)

            // Mengirim data yang diperlukan ke aktivitas pengeditan
            intent.putExtra("idSticker", currentitem.idSticker)
            intent.putExtra("nameSticker", currentitem.nameSticker)
            intent.putExtra("imageSticker", currentitem.imageSticker)
            intent.putExtra("harga", currentitem.harga)

            // Memulai aktivitas pengeditan
            context.startActivity(intent)
        }

        // Menambahkan OnClickListener ke tombol delete
        holder.icDelete.setOnClickListener {
            // Implementasi aksi yang sesuai di sini
            // Misalnya, Anda dapat memanggil sebuah fungsi untuk menghapus item.
        }
    }
}