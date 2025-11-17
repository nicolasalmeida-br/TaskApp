package com.senai.taskapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.senai.taskapp.viewmodel.TaskViewModel
import com.senai.taskapp.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etTaskTitle: EditText = findViewById(R.id.etTaskTitle)
        val btnAddTask: Button = findViewById(R.id.btnAddTask)
        val rvTasks: RecyclerView = findViewById(R.id.rvTasks)

        val adapter = TaskAdapter(
            onTaskChecked = { task ->
                taskViewModel.update(task)
            },
            onDeleteClicked = { task ->
                taskViewModel.delete(task)
            }
        )
        rvTasks.adapter = adapter

        taskViewModel.allTasks.observe(this) { tasks ->
            tasks?.let {
                adapter.submitList(it)
            }
        }

        btnAddTask.setOnClickListener {
            val title = etTaskTitle.text.toString().trim()
            if (title.isNotEmpty()) {
                taskViewModel.insert(title)
                etTaskTitle.setText("")
            }
        }
    }
}
