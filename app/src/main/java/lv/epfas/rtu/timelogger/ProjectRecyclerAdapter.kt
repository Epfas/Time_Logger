package lv.epfas.rtu.timelogger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_project_card.view.*
import kotlinx.android.synthetic.main.project_line.view.*


class ProjectRecyclerAdapter(
    private val listener: ProjectAdapterClickListener,
    private val items: MutableList<Project>
) :
    RecyclerView.Adapter<ProjectRecyclerAdapter.ProjectViewHolder>() {

    class ProjectViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.project_line, parent, false)
        return ProjectViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        holder.itemView.laCodeData.text = item.code
        holder.itemView.laNameData.text = item.name
        holder.itemView.chInternalPL.setChecked(item.internal)
        holder.itemView.chFavoritePL.setChecked(item.favorite)
        holder.itemView.chClosedPL.setChecked(item.closed)
        // holder.itemView.shoppingQuantity.text = context.resources
        //     .getString(R.string.quantity_text, item.quantity, item.unit)

        holder.itemView.setOnClickListener {
            listener.itemClicked(items[position])
        }

        holder.itemView.btnRemove.setOnClickListener {
            listener.deleteClicked(items[position])
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }
}