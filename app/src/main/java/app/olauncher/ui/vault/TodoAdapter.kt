package app.olauncher.ui.vault

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.olauncher.R
import app.olauncher.helper.TodoItem

class TodoAdapter(
    private var items: List<TodoItem>,
    private val onItemToggled: (TodoItem) -> Unit,
    private val onItemDeleted: ((TodoItem) -> Unit)? = null
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbTodo: CheckBox = view.findViewById(R.id.cbTodo)
        val tvTodoTitle: TextView = view.findViewById(R.id.tvTodoTitle)
        val btnDeleteTodo: ImageButton = view.findViewById(R.id.btnDeleteTodo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTodoTitle.text = item.title

        // Avoid triggering listener during binding
        holder.cbTodo.setOnCheckedChangeListener(null)
        holder.cbTodo.isChecked = item.isCompleted

        updateStrikethrough(holder.tvTodoTitle, item.isCompleted)

        holder.cbTodo.setOnCheckedChangeListener { _, isChecked ->
            item.isCompleted = isChecked
            updateStrikethrough(holder.tvTodoTitle, isChecked)
            onItemToggled(item)
        }

        if (onItemDeleted != null) {
            holder.btnDeleteTodo.visibility = View.VISIBLE
            holder.btnDeleteTodo.setOnClickListener {
                onItemDeleted.invoke(item)
            }
        } else {
            holder.btnDeleteTodo.visibility = View.GONE
        }
    }

    private fun updateStrikethrough(textView: TextView, isCompleted: Boolean) {
        if (isCompleted) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textView.alpha = 0.5f
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textView.alpha = 1.0f
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<TodoItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
