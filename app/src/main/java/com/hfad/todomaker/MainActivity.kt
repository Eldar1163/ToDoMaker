package com.hfad.todomaker

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm

class MainActivity : AppCompatActivity() {
    lateinit var rvTasks: RecyclerView
    lateinit var taskList: MutableList<Task>

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_main)

        /*val task1 = Task("Собрать портфель", true)
        val task2 = Task("Вымыть посуду", false)
        taskList = mutableListOf(task1, task2)*/
        Realm.init(this)
        taskList = retrieveTaskList()

        rvTasks = findViewById(R.id.rvTasks)
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = TaskAdapter(taskList)
    }

    fun addTaskClick(view: View) {
        val manager = supportFragmentManager
        val createTaskDialog = CreateTaskDialog()
        createTaskDialog.show(manager, "ADD")
    }

    fun addBtnClicked(title: String) {
        val task = Task(title, false)
        taskList.add(task)
        addTaskToDB(task)
        rvTasks.adapter?.notifyItemInserted(taskList.size - 1)
    }

    fun retrieveTaskList(): MutableList<Task> {
        val realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        var result = realm.where(Task::class.java).findAll().toMutableList()
        realm.commitTransaction()

        return result
    }

    fun addTaskToDB(task: Task) {
        val realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        var t = realm.createObject(Task::class.java)
        t.isComplete = task.isComplete
        t.title = task.title
        realm.commitTransaction()
    }
}