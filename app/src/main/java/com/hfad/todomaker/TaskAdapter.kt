package com.hfad.todomaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.annotations.RealmClass
import io.realm.kotlin.where


class TaskAdapter(var tasks: MutableList<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

        class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var checkBox: CheckBox? = null
            var titleTextView: TextView? = null
            var btnDelete: Button? = null

            init {
                checkBox = itemView.findViewById(R.id.cb_complete)
                titleTextView = itemView.findViewById(R.id.tv_title)
                btnDelete = itemView.findViewById(R.id.btn_delete)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_element_layout, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.titleTextView?.text = tasks[position].title
        holder.checkBox?.isChecked = tasks[position].isComplete
        holder.checkBox?.setOnClickListener {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            tasks[position].isComplete = (it as CheckBox).isChecked
            val result = realm.where(Task::class.java)
                .equalTo("title", tasks[position].title)
                .findFirst()
            if (result != null)
                result.isComplete = tasks[position].isComplete
            notifyItemChanged(position)
            notifyDataSetChanged()
            realm.commitTransaction()
        }
        holder.btnDelete?.setOnClickListener {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            val result = realm.where(Task::class.java)
                .equalTo("title", tasks[position].title)
                .findAll()
            for (task in result)
                task.deleteFromRealm()
            tasks.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tasks.size)
            realm.commitTransaction()
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    }
