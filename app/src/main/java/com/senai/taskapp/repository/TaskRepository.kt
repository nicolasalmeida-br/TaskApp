package com.senai.taskapp.repository

import com.senai.taskapp.data.TaskDao
import com.senai.taskapp.data.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun insert(task: TaskEntity) = taskDao.insert(task)

    suspend fun update(task: TaskEntity) = taskDao.update(task)

    suspend fun delete(task: TaskEntity) = taskDao.delete(task)
}