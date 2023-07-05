package umbjm.ft.inf.mydigitalprinting.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umbjm.ft.inf.mydigitalprinting.R

class GridAdapter(private val gridList:ArrayList<GridItem>) : RecyclerView.Adapter<GridAdapter.GridViewHolder>(){

    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image_grid: ImageView = itemView.findViewById(R.id.image_grid)
        val text_grid: TextView = itemView.findViewById(R.id.text_grid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return GridViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gridList.size
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val grid = gridList[position]
        holder.image_grid.setImageResource(grid.image)
        holder.text_grid.text = grid.name
    }
}