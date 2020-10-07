package lv.epfas.rtu.timelogger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_panel.view.*
import lv.epfas.rtu.timelogger.R

class LoggerStateRecyclerAdapter(
    private val listener: LoggerStateAdapterClickListener,
    private val items: MutableList<LoggerState>
) :
    RecyclerView.Adapter<LoggerStateRecyclerAdapter.LoggerStateViewHolder>() {

    class LoggerStateViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoggerStateViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_panel, parent, false)
        return LoggerStateViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: LoggerStateViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        holder.itemView.laProjectValue.text = item.projectCode + "  -  " + item.projectName
        holder.itemView.laTaskDescrValue.text = item.taskDescription
        holder.itemView.laTimeValue.text = "XX:YY"
        // holder.itemView.chClosed.isChecked =
        // holder.itemView.shoppingQuantity.text = context.resources
        //     .getString(R.string.quantity_text, item.quantity, item.unit)


        holder.itemView.setOnClickListener {
            listener.itemClicked(items[position])
        }

        holder.itemView.btnFinishTask.setOnClickListener {
            listener.finishClicked(items[position])
            items.removeAt(position)

            notifyDataSetChanged()
        }
    }
}