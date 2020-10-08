package lv.epfas.rtu.timelogger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.project_line.view.*

class ProjectTaskRecyclerAdapter(
    private val listener: ProjectTaskAdapterClickListener,
    private val items: MutableList<ProjectTask>
) :
    RecyclerView.Adapter<ProjectTaskRecyclerAdapter.ProjectTaskViewHolder>() {

    class ProjectTaskViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectTaskViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.logger_entry, parent, false)
        return ProjectTaskViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ProjectTaskViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        holder.itemView.laCodeData.text = item.externalId
        holder.itemView.laNameData.text = item.description
        // holder.itemView.chClosed.isChecked =
        // holder.itemView.shoppingQuantity.text = context.resources
        //     .getString(R.string.quantity_text, item.quantity, item.unit)

        holder.itemView.setOnClickListener {
            listener.itemClicked(items[position])
        }

        holder.itemView.btnRemove.setOnClickListener {
            listener.startClicked(items[position])
            // items.removeAt(position)
            notifyDataSetChanged()
        }
    }
}