package com.senai.taskapp

import android.graphics.Paint
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.ListAdapter
import com.senai.taskapp.data.TaskEntity

class TaskAdapter(
    private val onTaskChecked: (TaskEntity) -> Unit,
    private val onDeleteClicked: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.tvTaskTitle)
        private val cb: CheckBox = view.findViewById(R.id.cbTaskCompleted)
        private val deleteBtn: ImageButton = view.findViewById(R.id.btnDeleteTask)

        fun bind(task: TaskEntity) {
            title.text = task.title
            cb.isChecked = task.isCompleted

            title.paintFlags =
                if (task.isCompleted)
                    title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                else
                    title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            cb.setOnCheckedChangeListener { _, isChecked ->
                onTaskChecked(task.copy(isCompleted = isChecked))
            }

            deleteBtn.setOnClickListener {
                onDeleteClicked(task)
            }
        }
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(o: TaskEntity, n: TaskEntity) = o.id == n.id
    override fun areContentsTheSame(o: TaskEntity, n: TaskEntity) = o == n
}