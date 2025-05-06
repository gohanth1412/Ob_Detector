package co.spirbase.framework.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.spirbase.R
import co.spirbase.databinding.ViewItemTodoBinding
import co.spirbase.framework.model.TodoModel

class TodoAdapter : ListAdapter<TodoModel, TodoAdapter.TodoViewHolder>(DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<TodoModel>() {
        override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener {
        fun onTodoClick(item: TodoModel)
        fun onCheckClick(item: TodoModel)
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    inner class TodoViewHolder(val binding: ViewItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoModel) {
            binding.run {
                tvName.text = item.name
                tvDes.text = item.des
            }

            if (item.isSelected) {
                binding.ivSelect.setImageResource(R.drawable.ic_selected)
            } else {
                binding.ivSelect.setImageResource(R.drawable.ic_un_selected)
            }

            binding.llMain.setOnClickListener {
                listener?.onTodoClick(item)
            }

            binding.ivSelect.setOnClickListener {
                listener?.onCheckClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            ViewItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}