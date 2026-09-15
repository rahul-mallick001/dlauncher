package app.olauncher.ui.vault

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.olauncher.R
import app.olauncher.helper.VaultManager

class TodoListDialogFragment : DialogFragment() {

    private lateinit var vaultManager: VaultManager
    private lateinit var adapter: TodoAdapter
    var onDismissCallback: (() -> Unit)? = null

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissCallback?.invoke()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vaultManager = VaultManager(requireContext())

        val rvTodos = view.findViewById<RecyclerView>(R.id.rvTodos)
        val etNewTodo = view.findViewById<EditText>(R.id.etNewTodo)
        val btnAddTodo = view.findViewById<Button>(R.id.btnAddTodo)
        val btnCloseTodos = view.findViewById<Button>(R.id.btnCloseTodos)

        rvTodos.layoutManager = LinearLayoutManager(requireContext())
        adapter = TodoAdapter(
            items = vaultManager.getTodoList(),
            onItemToggled = { item ->
                vaultManager.toggleTodo(item.id)
            },
            onItemDeleted = { item ->
                vaultManager.removeTodo(item.id)
                adapter.updateList(vaultManager.getTodoList())
            }
        )
        rvTodos.adapter = adapter

        btnAddTodo.setOnClickListener {
            val text = etNewTodo.text.toString().trim()
            if (text.isNotEmpty()) {
                vaultManager.addTodo(text)
                etNewTodo.text.clear()
                adapter.updateList(vaultManager.getTodoList())
            }
        }

        btnCloseTodos.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.90).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        fun newInstance(): TodoListDialogFragment = TodoListDialogFragment()
    }
}
