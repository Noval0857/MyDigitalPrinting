//package umbjm.ft.inf.mydigitalprinting.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import umbjm.ft.inf.mydigitalprinting.R
//import umbjm.ft.inf.mydigitalprinting.model.UserModel
//
//class UserAdapter (val listUsers:ArrayList<UserModel>,
//                      val listener:OnAdapterListener):
//    RecyclerView.Adapter<UserAdapter.Holder>() {
//    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        var id = 0
//        var nama: TextView = itemView.findViewById(R.id.nama)
//        var email: TextView = itemView.findViewById(R.id.email)
//        var password: TextView = itemView.findViewById(R.id.password)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view: View = LayoutInflater
//            .from(parent.context)
//            .inflate(
//                R.layout.activity_registrasi,
//                parent,
//                false)
//        return Holder(view)
//    }
//
//    override fun getItemCount(): Int = listUsers.size
//
//    override fun onBindViewHolder(
//        holder: Holder,
//        position: Int
//    ) {
//        val user = listUsers[position]
//
//        holder.id = user.id!!
//        holder.nama.text = user.nama
//        holder.email.text = user.email
//        holder.password.text = user.password
//
//    }
//
//    interface OnAdapterListener{
//        fun onUpdate(user: UserModel)
//        fun onDelete(user: UserModel)
//    }
//}