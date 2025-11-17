package com.senai.taskapp

import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import com.senai.taskapp.data.TaskDatabase
import com.senai.taskapp.repository.TaskRepository

class TaskApplication() : Application(), Parcelable {
    val database by lazy { TaskDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskApplication> {
        override fun createFromParcel(parcel: Parcel): TaskApplication {
            return TaskApplication(parcel)
        }

        override fun newArray(size: Int): Array<TaskApplication?> {
            return arrayOfNulls(size)
        }
    }
}